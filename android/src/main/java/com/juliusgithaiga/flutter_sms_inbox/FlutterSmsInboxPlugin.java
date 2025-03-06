package com.juliusgithaiga.flutter_sms_inbox;

import android.content.Context;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** FlutterSmsInboxPlugin */
public class FlutterSmsInboxPlugin implements FlutterPlugin, MethodCallHandler {
  private static final String CHANNEL_QUERY = "plugins.juliusgithaiga.com/querySMS";
  private MethodChannel methodChannel;
  private MethodChannel querySmsChannel;

  /** Plugin registration for older Flutter versions. 
   * Note: This method is removed as the Registrar class is deprecated
   * and not available in newer Flutter versions.
   * The plugin now uses the recommended FlutterPlugin interface methods below.
   */
  // Legacy registration code removed to fix compilation error

  @Override
  public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
    onAttachedToEngine(binding.getApplicationContext(), binding.getBinaryMessenger());
  }

  private void onAttachedToEngine(Context appContext, BinaryMessenger messenger) {
    methodChannel = new MethodChannel(messenger, "flutter_sms_inbox");
    methodChannel.setMethodCallHandler(this);
    querySmsChannel = new MethodChannel(messenger, CHANNEL_QUERY, JSONMethodCodec.INSTANCE);
    querySmsChannel.setMethodCallHandler(new SmsQuery(appContext));
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
    querySmsChannel.setMethodCallHandler(null);
    methodChannel = querySmsChannel = null;
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }
}
