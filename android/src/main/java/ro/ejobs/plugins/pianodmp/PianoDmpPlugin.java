package ro.ejobs.plugins.pianodmp;

import android.Manifest;
import android.util.Log;
import com.cxense.cxensesdk.CredentialsProvider;
import com.cxense.cxensesdk.CxenseConfiguration;
import com.cxense.cxensesdk.CxenseSdk;
import com.cxense.cxensesdk.model.PageViewEvent;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import java.util.Iterator;

@CapacitorPlugin(name = "PianoDmp", permissions = { @Permission(alias = "phoneState", strings = { Manifest.permission.READ_PHONE_STATE }) })
public class PianoDmpPlugin extends Plugin {

    private PianoDmp implementation = new PianoDmp();

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
