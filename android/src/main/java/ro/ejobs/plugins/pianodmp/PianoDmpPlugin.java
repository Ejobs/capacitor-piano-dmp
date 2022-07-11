package ro.ejobs.plugins.pianodmp;

import android.Manifest;
import android.util.Log;
import com.cxense.cxensesdk.CredentialsProvider;
import com.cxense.cxensesdk.CxenseConfiguration;
import com.cxense.cxensesdk.CxenseSdk;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

@CapacitorPlugin(name = "PianoDmp", permissions = { @Permission(alias = "phoneState", strings = { Manifest.permission.READ_PHONE_STATE }) })
public class PianoDmpPlugin extends Plugin {

    private PianoDmp implementation = new PianoDmp();
    private CxenseConfiguration cxConfig;

    @Override
    public void load() {
        super.load();

        cxConfig = CxenseSdk.getInstance().getConfiguration();

        String pianoUsername = getConfig().getString("username");
        String pianoApiKey = getConfig().getString("apiKey");

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
    }

    @PluginMethod
    public void sendPageView(PluginCall call) {
        if (getPermissionState("phoneState") != PermissionState.GRANTED) {
            requestPermissionForAlias("phoneState", call, "onPermissionGranted");
        } else {
            implementation.sendPageView(call, getConfig());
        }
    }

    @PermissionCallback
    private void onPermissionGranted(PluginCall call) {
        if (getPermissionState("phoneState") == PermissionState.GRANTED) {
            implementation.sendPageView(call, getConfig());
        } else {
            call.reject("Permissions required for making Piano API calls");
        }
    }
}
