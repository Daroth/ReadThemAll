package org.braindead.readthemall;

import org.apache.http.HttpResponse;
import org.braindead.readthemall.api.JsonApiClient;
import org.braindead.readthemall.api.Link;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RTAShareActivity extends RTAActivity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		String subject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
		String link = intent.getStringExtra(Intent.EXTRA_TEXT);
		this.setContentView(R.layout.share);
		EditText editSubject = (EditText) this.findViewById(R.id.EditSubject);
		EditText editLink = (EditText) this.findViewById(R.id.EditLink);
		Button submitButton = (Button) this.findViewById(R.id.SaveALinkButtonShare);
		submitButton.setOnClickListener(this);
		editSubject.setText(subject);
		editLink.setText(link);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.SaveALinkButtonShare) {
			EditText editSubject = (EditText) this.findViewById(R.id.EditSubject);
			EditText editLink = (EditText) this.findViewById(R.id.EditLink);
			Link link = new Link(editLink.getText().toString(), editSubject.getText().toString());
			HttpResponse result = JsonApiClient.setLinks(RTAActivity.API_URL+"api/links", link);
			if(result.getStatusLine().getStatusCode() != 200) {
				Toast.makeText(this, "Something went wrong, try again later.", Toast.LENGTH_LONG);
			} else {
				Intent intent = new Intent(RTAShareActivity.this, RTAMainActivity.class);
				this.startActivity(intent);
			}
		}
	}
}
