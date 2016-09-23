package myappplication.noida.quaere.groupalternate.swipetab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import myappplication.noida.quaere.groupalternate.R;
import myappplication.noida.quaere.groupalternate.mainpage.FeedbackActivity;

public class ContactUsFragment extends Fragment {
Button feedback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
		);
		rootView.findViewById(R.id.frag_feedback_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), FeedbackActivity.class));
			}
		});
		return rootView;
	}

	/*private class MyWebViewClient extends WebViewClient {
		private ProgressBar progress;
		Activity activity;

		public MyWebViewClient(Activity activity, ProgressBar progress) {
			this.activity = activity;
			this.progress = progress;
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			progress.setVisibility(View.VISIBLE);
			progress.setProgress(100);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			progress.setVisibility(View.GONE);
			progress.setProgress(100);
			super.onPageFinished(view, url);
		}

		public void setValue(int progress) {
			this.progress.setProgress(progress);
		}
	}*/
}
