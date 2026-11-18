package listener;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import bean.FamilyBD;
import dao.FamilyBDDAO;
import dao.GiftDAO;
import dao.NotificationDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

// 誕生日通知を毎日自動で確認するクラス
@WebListener
public class BirthdayNotificationListener
        implements ServletContextListener {

    private ScheduledExecutorService executor;

    // 日本時間
    private static final ZoneId ZONE =
            ZoneId.of("Asia/Tokyo");

    // Tomcat起動時に自動実行する
    @Override
    public void contextInitialized(ServletContextEvent event) {

        executor = Executors.newSingleThreadScheduledExecutor();

        // 起動後すぐに1回確認する
        executor.schedule(
            (Callable<Void>) () -> {

                // 次回の実行を予約する
                scheduleNext();

                // 誕生日通知を確認する
                sendBirthdayNotifications();

                return null;
            },
            0,
            TimeUnit.SECONDS
        );
    }

    // Tomcat終了時に自動処理を停止する
    @Override
    public void contextDestroyed(ServletContextEvent event) {

        if (executor != null) {
            executor.shutdownNow();
        }
    }

    // 次の日の午前0時5分に実行するよう予約する
    private void scheduleNext() {

        ZonedDateTime now = ZonedDateTime.now(ZONE);

        ZonedDateTime nextRun = now.toLocalDate()
                .plusDays(1)
                .atTime(0, 5)
                .atZone(ZONE);

        long delay = Duration.between(
                now,
                nextRun
        ).toMillis();

        executor.schedule(
            (Callable<Void>) () -> {

                // 次回の実行を予約する
                scheduleNext();

                // 誕生日通知を確認する
                sendBirthdayNotifications();

                return null;
            },
            delay,
            TimeUnit.MILLISECONDS
        );
    }

    // 誕生日通知を確認して登録する
    private void sendBirthdayNotifications() throws Exception {

        // 日本時間で今日の日付を取得する
        LocalDate today = LocalDate.now(ZONE);

        // DAOを生成
        FamilyBDDAO familyBDDAO = new FamilyBDDAO();
        GiftDAO giftDAO = new GiftDAO();
        NotificationDAO notificationDAO = new NotificationDAO();

        // 全ユーザーの家族の誕生日を取得する
        List<FamilyBD> familyList = familyBDDAO.findAll();

        // 家族ごとに確認する
        for (FamilyBD family : familyList) {

            // 登録されている誕生日
            LocalDate birthday = family.getBirthday();

            // 今年の誕生日を取得する
            LocalDate nextBirthday;

            if (birthday.getMonthValue() == 2
                    && birthday.getDayOfMonth() == 29
                    && !Year.isLeap(today.getYear())) {

                // うるう年でない場合は2月28日とする
                nextBirthday = LocalDate.of(
                        today.getYear(),
                        2,
                        28
                );

            } else {

                nextBirthday = birthday.withYear(
                        today.getYear()
                );
            }

            // 今年の誕生日が過ぎている場合は翌年にする
            if (nextBirthday.isBefore(today)) {

                int nextYear = today.getYear() + 1;

                if (birthday.getMonthValue() == 2
                        && birthday.getDayOfMonth() == 29
                        && !Year.isLeap(nextYear)) {

                    nextBirthday = LocalDate.of(
                            nextYear,
                            2,
                            28
                    );

                } else {

                    nextBirthday = birthday.withYear(nextYear);
                }
            }

            // ギフトを選択できる開始日
            LocalDate startDate = nextBirthday.minusMonths(1);

            // 通知対象の日付か確認する
            boolean oneMonthNotice = today.equals(startDate);
            boolean birthdayNotice = today.equals(nextBirthday);

            // 通知対象の日付でなければ次の家族へ
            if (!oneMonthNotice && !birthdayNotice) {
                continue;
            }

            // ギフトが登録済みか確認する
            boolean registered = giftDAO.existsHistoryInPeriod(
                family.getUserId(),
                family.getFamilyBirthdayId(),
                Date.valueOf(startDate),
                Date.valueOf(nextBirthday)
            );

            // すでにギフトが登録されている場合は通知しない
            if (registered) {
                continue;
            }

            // 通知の種類を設定する
            String noticeType;
            String message;

            if (oneMonthNotice) {

                noticeType = "ONE_MONTH";

                message =
                    "プレゼント選びのお知らせ\n"
                    + family.getRelation()
                    + "の誕生日まであと1か月です。\n"
                    + "プレゼントを選びましょう。";

            } else {

                noticeType = "TODAY";

                message =
                    "プレゼント選びのお知らせ\n"
                    + "今日は"
                    + family.getRelation()
                    + "の誕生日です。\n"
                    + "まだプレゼントが登録されていません。";

            }

            // 重複確認用の識別情報
            String notificationKey =
                "[[BIRTHDAY_NOTICE:"
                + noticeType
                + ":"
                + family.getFamilyBirthdayId()
                + ":"
                + nextBirthday.toString()
                + "]]";

            // メッセージに識別情報を追加する
            String notificationMessage =
                message + "\n" + notificationKey;

            // 誕生日通知を登録する
            notificationDAO.insertBirthdayNotification(
                family.getUserId(),
                notificationMessage,
                notificationKey
            );
        }
    }
}