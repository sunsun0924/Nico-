
package tool;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Tavily APIとの通信を行うクラス
public class TavilyService {

    // Tavily APIのURL
    private static final String API_URL =
        "https://api.tavily.com/search";

    // HTTPクライアントを共通利用
    private static final HttpClient CLIENT =
        HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    // TavilyでWeb検索を行い、検索結果を取得する
    public List<Map<String, String>> search(String query)
            throws Exception {

        // 環境変数からAPIキーを取得
        String apiKey = System.getenv("TAVILY_API_KEY");

        // APIキーが取得できない場合
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                "環境変数 TAVILY_API_KEY が設定されていません。"
            );
        }

        // 検索キーワードの入力確認
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "検索キーワードが入力されていません。"
            );
        }

        // リクエスト用JSONを作成
        JsonObject requestBody = new JsonObject();

        requestBody.addProperty("api_key", apiKey);
        requestBody.addProperty("query", query.trim());
        requestBody.addProperty("topic", "general");
        requestBody.addProperty("search_depth", "basic");
        requestBody.addProperty("max_results", 5);
        requestBody.addProperty("include_answer", false);

        // HTTPリクエストを作成
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Content-Type", "application/json")
            .timeout(Duration.ofSeconds(60))
            .POST(HttpRequest.BodyPublishers.ofString(
                requestBody.toString()
            ))
            .build();

        System.out.println("Tavily検索開始");
        System.out.println("検索キーワード：" + query);

        // Tavily APIに送信
        HttpResponse<String> response = CLIENT.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        System.out.println(
            "Tavily HTTPステータス：" + response.statusCode()
        );

        // HTTPエラーを確認
        if (response.statusCode() != 200) {
            throw new Exception(
                "Tavily APIエラー: HTTP "
                + response.statusCode()
                + "\n詳細: " + response.body()
            );
        }

        // レスポンスJSONを解析
        JsonObject responseBody =
            JsonParser.parseString(response.body())
                .getAsJsonObject();

        // 検索結果を取得
        JsonArray results =
            responseBody.getAsJsonArray("results");

        // 検索結果を格納するリスト
        List<Map<String, String>> searchResults =
            new ArrayList<>();

        // 検索結果がない場合
        if (results == null || results.size() == 0) {

            System.out.println(
                "Tavilyの検索結果はありませんでした。"
            );

            return searchResults;
        }

        // 検索結果をリストに追加
        for (int i = 0; i < results.size(); i++) {

            JsonObject result =
                results.get(i).getAsJsonObject();

            String title = getValue(result, "title");
            String url = getValue(result, "url");
            String content = getValue(result, "content");

            // 1件分の検索結果を作成
            Map<String, String> resultMap =
                new LinkedHashMap<>();

            resultMap.put("title", title);
            resultMap.put("url", url);
            resultMap.put("content", content);

            searchResults.add(resultMap);
        }

        System.out.println(
            "Tavily検索結果：" + searchResults.size() + "件"
        );

        return searchResults;
    }

    // JSONから文字列を取得
    private String getValue(JsonObject object, String key) {

        JsonElement element = object.get(key);

        if (element == null || element.isJsonNull()) {
            return "";
        }

        return element.getAsString();
    }
}
