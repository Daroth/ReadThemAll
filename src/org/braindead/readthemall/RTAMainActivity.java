package org.braindead.readthemall;

import java.util.List;

import org.braindead.readthemall.api.JsonApiClient;
import org.braindead.readthemall.api.Link;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class RTAMainActivity extends RTAActivity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Link> listLinks = JsonApiClient.getLinks(RTAActivity.API_URL+"api/links"); 
        setContentView(R.layout.main);
        if(listLinks != null && listLinks.size() > 0) {
        	String[] links = new String[listLinks.size()];
        	int i=0;
        	for(Link link: listLinks) {
        		links[i++] = link.getLink()+"\n"+link.getTitle();
        	}
        	ListView listLinksView = (ListView) this.findViewById(R.id.ListLinksMain);
        	listLinksView.setAdapter(new  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, links));
        }
        
        Button addALinkButton = (Button) this.findViewById(R.id.AddALinkButtonMain);
        addALinkButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.AddALinkButtonMain) {
			Intent intent = new Intent(RTAMainActivity.this, RTAShareActivity.class);
			this.startActivity(intent);
		}
	}
}