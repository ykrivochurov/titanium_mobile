package org.appcelerator.titanium;

import java.io.IOException;

import org.appcelerator.titanium.util.TitaniumUIHelper;
import org.appcelerator.titanium.util.TitaniumUrlHelper;

import android.content.Intent;
import android.net.Uri;
import android.util.Config;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TiWebViewClient extends WebViewClient
{
	private static final String LCAT = "TiWVC";
	private static final boolean DBG = Config.LOGD;

	private final TitaniumActivity activity;

	public TiWebViewClient(TitaniumActivity activity)
	{
		super();
		this.activity = activity;
	}

	@Override
	public void onPageFinished (WebView view, String url)
	{
		super.onPageFinished(view,url);
		if (DBG) {
			Log.d(LCAT, "Page Finished");
		}
		if (activity.getLoadOnPageEnd()) {
			activity.triggerLoad();
		}
	}

	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
	{
		super.onReceivedError(view, errorCode, description, failingUrl);
		String text = "Err("+errorCode+") " + description;
		TitaniumUIHelper.doKillOrContinueDialog(view.getContext(), "Resource Not Found", text, null, null);
		Log.e(LCAT, "Received on error" + text);
	}

	@Override
	public boolean shouldOverrideUrlLoading(final WebView view, String url) {
		if (DBG) {
			Log.d(LCAT, "url=" + url);
		}

		if (URLUtil.isAssetUrl(url) || URLUtil.isContentUrl(url) || URLUtil.isFileUrl(url)) {
			boolean result = false;
			try {
				result = TitaniumUrlHelper.loadFromSource(view, url, null);
			} catch (IOException e) {
            	TitaniumUIHelper.doOkDialog(
            			view.getContext(),
            			"Fatal",
            			"Error loading source: " + e.getMessage(),
            			TitaniumUIHelper.createKillListener()
            			);
			}
			return result;
		} else if (URLUtil.isNetworkUrl(url)) {
            Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse(url) );
            i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(i);
            return true;
		} else {
			if (DBG) {
				Log.e(LCAT, "NEED to Handle " + url);
			}
		}

		return false;
	}
}
