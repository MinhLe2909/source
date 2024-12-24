package tamhoang.ldpro4.Telegram;


import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

public class TelegramClient {
    private static Client client;

    public interface Callback extends Client.ResultHandler {
        @Override
        void onResult(TdApi.Object object);
    }

    private TelegramClient() {
    }

    public static Client getClient(Callback activity) {
        if (client == null) {
            try {
                client = Client.create(activity, null, null);
            } catch (Exception e) {
            }
        }
        return client;
    }
}