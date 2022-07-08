package ro.ejobs.plugins.pianodmp;

import android.util.Log;

import com.cxense.cxensesdk.CredentialsProvider;
import com.cxense.cxensesdk.CxenseConfiguration;
import com.cxense.cxensesdk.CxenseSdk;
import com.cxense.cxensesdk.model.PageViewEvent;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.Iterator;

@CapacitorPlugin(name = "PianoDmp")
public class PianoDmpPlugin extends Plugin {

    // private PianoDmp implementation = new PianoDmp();
    CxenseConfiguration config = CxenseSdk.getInstance().getConfiguration();
    PageViewEvent.Builder builder = new PageViewEvent.Builder("1136227972865927406");

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void sendPageView(PluginCall call) {
        if (!call.getData().has("location")) {
            call.reject("Missing required location parameter");
            return;
        }

        try {
            config.setCredentialsProvider(new CredentialsProvider() {
                @Override
                public String getUsername() {
                    return "SEXY-EMAIL"; // load it from secured storage
                }
                @Override
                public String getApiKey() {
                    return "SEXY-KEY"; // load it from secured storage
                }
            });

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
                        builder.addCustomUserParameter(customParamKey, customParams.getString(customParamKey));
                    }
                }
            }

            CxenseSdk.getInstance().pushEvents(builder.build());
        } catch (Exception e) {
            Log.e("Cxense error", e.getMessage());
            call.reject(e.getMessage(), e);
            return;
        }

        call.resolve();
    }
}
