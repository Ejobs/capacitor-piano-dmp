package ro.ejobs.plugins.pianodmp;

import android.util.Log;
import com.cxense.cxensesdk.CredentialsProvider;
import com.cxense.cxensesdk.CxenseConfiguration;
import com.cxense.cxensesdk.CxenseSdk;
import com.cxense.cxensesdk.model.PageViewEvent;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginConfig;
import java.util.Iterator;

public class PianoDmp {

    CxenseConfiguration cxConfig = CxenseSdk.getInstance().getConfiguration();

    public void sendPageView(PluginCall call, PluginConfig config) {
        String siteId = config.getString("siteId");
        if (siteId == null || siteId == "") {
            call.reject("Missing required siteId param");
            return;
        }

        String pianoUsername = config.getString("username");
        if (pianoUsername == null || pianoUsername == "") {
            call.reject("Missing required username param");
            return;
        }

        String pianoApiKey = config.getString("apiKey");
        if (pianoApiKey == null || pianoApiKey == "") {
            call.reject("Missing required api key param");
            return;
        }

        PageViewEvent.Builder builder = new PageViewEvent.Builder(siteId);
        if (!call.getData().has("location")) {
            call.reject("Missing required location parameter");
            return;
        }

        try {
            cxConfig.setCredentialsProvider(
                new CredentialsProvider() {
                    @Override
                    public String getUsername() {
                        return pianoUsername;
                    }

                    @Override
                    public String getApiKey() {
                        return pianoApiKey;
                    }
                }
            );

            String location = call.getString("location");
            builder.setLocation(location);

            if (call.getData().has("userParams")) {
                JSObject userParams = call.getObject("userParams", new JSObject());
                Iterator<String> userParamsKeys = userParams.keys();

                while (userParamsKeys.hasNext()) {
                    String userParamKey = userParamsKeys.next();
                    if (userParams.has(userParamKey)) {
                        builder.addCustomUserParameter(userParamKey, userParams.getString(userParamKey));
                    }
                }
            }

            if (call.getData().has("customParams")) {
                JSObject customParams = call.getObject("customParams", new JSObject());
                Iterator<String> customParamsKeys = customParams.keys();

                while (customParamsKeys.hasNext()) {
                    String customParamKey = customParamsKeys.next();
                    if (customParams.has(customParamKey)) {
                        builder.addCustomParameter(customParamKey, customParams.getString(customParamKey));
                    }
                }
            }

            CxenseSdk.getInstance().pushEvents(builder.build());
        } catch (Exception e) {
            Log.e("Cxense error", e.getMessage());
            call.reject(e.getMessage(), e);
            return;
        }
    }
}
