
package tool;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Gemini APIとの通信を行うクラス
public class GeminiService {

    // 使用するGeminiモデル
	private static final String MODEL = "gemini-3.5-flash-lite";

    // Gemini Interactions APIのURL
    private static final String API_URL =
        "https://generativelanguage.googleapis.com/v1beta/interactions";

    // 最大試行回数
    private static final int MAX_ATTEMPTS = 3;

    // HTTPクライアントを共通利用
    private static final HttpClient CLIENT = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(30))
        .build();

    // Geminiに質問を送り、回答を取得する
    public String generateResponse(String prompt) throws Exception {

        // 環境変数からAPIキーを取得
        String apiKey = System.getenv("GEMINI_API_KEY");

        // APIキーが取得できない場合
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                "環境変数 GEMINI_API_KEY が設定されていません。"
            );
        }

        // リクエスト用JSONを作成
        JsonObject requestBody = new JsonObject();

        requestBody.addProperty("model", MODEL);
        requestBody.addProperty("input", prompt);

        // HTTPリクエストを作成
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Content-Type", "application/json")
            .header("x-goog-api-key", apiKey)
            .timeout(Duration.ofSeconds(120))
            .POST(HttpRequest.BodyPublishers.ofString(
                requestBody.toString()
            ))
            .build();

        System.out.println("使用モデル：" + MODEL);
        System.out.println("API URL：" + API_URL);
        System.out.println("Google検索機能：無効");

        HttpResponse<String> response = null;

        // Gemini APIへの送信を最大3回試行
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            long startTime = System.currentTimeMillis();

            System.out.println(
                "Gemini送信開始：" + attempt + "回目"
            );

            try {

                response = CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
                );

            } catch (HttpTimeoutException e) {

                System.err.println(
                    "Geminiとの通信がタイムアウトしました。"
                );

                throw e;
            }

            long elapsedTime =
                System.currentTimeMillis() - startTime;

            System.out.println(
                "Gemini通信時間：" + elapsedTime + "ミリ秒"
            );

            // 正常に通信できた場合
            if (response.statusCode() == 200) {
                break;
            }

            int statusCode = response.statusCode();

            // 429は利用枠超過の可能性があるため再試行しない
            if (statusCode == 429) {
                break;
            }

            // 503の場合は再試行
            if (statusCode == 503 && attempt < MAX_ATTEMPTS) {

                long waitTime = 2000L * attempt;

                System.out.println(
                    "Gemini APIエラー：" + statusCode
                    + "。" + (waitTime / 1000)
                    + "秒後に再試行します。"
                );

                try {

                    Thread.sleep(waitTime);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                    throw new Exception(
                        "Geminiの再試行が中断されました。",
                        e
                    );
                }

            } else {

                // 再試行対象外、または最大試行回数に到達
                break;
            }
        }

        // HTTPエラーを確認
        if (response == null || response.statusCode() != 200) {

            if (response == null) {
                throw new Exception(
                    "Geminiからレスポンスを取得できませんでした。"
                );
            }

            throw new Exception(
                "Gemini APIエラー: HTTP "
                + response.statusCode()
                + "\n詳細: " + response.body()
            );
        }

        // レスポンスJSONを解析
        JsonObject responseBody =
            JsonParser.parseString(response.body())
                .getAsJsonObject();

        // 回答を格納する
        StringBuilder answer = new StringBuilder();

        // Interactions APIのstepsを取得
        JsonArray steps = responseBody.getAsJsonArray("steps");

        if (steps == null) {
            throw new Exception(
                "Geminiのレスポンスにstepsがありません。"
                + "\n詳細: " + response.body()
            );
        }

        // 各ステップを確認
        for (int i = 0; i < steps.size(); i++) {

            JsonObject step = steps.get(i).getAsJsonObject();

            String type = step.has("type")
                ? step.get("type").getAsString()
                : "";

            // Geminiの回答を取得
            if ("model_output".equals(type)) {

                if (!step.has("content")) {
                    continue;
                }

                JsonArray contents =
                    step.getAsJsonArray("content");

                for (int j = 0; j < contents.size(); j++) {

                    JsonObject content =
                        contents.get(j).getAsJsonObject();

                    // テキスト形式の回答
                    if ("text".equals(
                            content.has("type")
                                ? content.get("type").getAsString()
                                : ""
                        )) {

                        if (content.has("text")) {

                            answer.append(
                                content.get("text").getAsString()
                            );
                        }
                    }
                }
            }
        }

        // 回答が取得できなかった場合
        if (answer.length() == 0) {

            throw new Exception(
                "Geminiの回答テキストを取得できませんでした。"
                + "\n詳細: " + response.body()
            );
        }

        return answer.toString();
    }
}
