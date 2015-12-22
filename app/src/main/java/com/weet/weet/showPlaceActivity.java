package com.weet.weet;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class showPlaceActivity extends AppCompatActivity {

    final double PI = 3.14159265358979323846;
    String groupID;
    //ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Group");
    //ParseGeoPoint point;
    //ParseObject group = new ParseObject("Group");
    //ParseObject group = ParseObject.fetchAll("Group");
    ParseObject obj = new ParseObject("Delete");
    ArrayList<String> recipient_id = new ArrayList<>();

    double lat, longi;
    int totWeight;
    double sum;

    ArrayList<Double> finalList = new ArrayList<>();
    ArrayList<Double> distances = new ArrayList<>();
    ArrayList<Double> locations = new ArrayList<>();
    Map<String, Double> map = new HashMap<>();
    //ArrayList<String> members2 = new ArrayList<>();

    //JSONArray members = new JSONArray();
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_place);
        setTitle(R.string.app_name);
        //Setting statusBar color to ColorPrimaryDark
        getWindow().setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark));

        // Creating The Toolbar and setting it as the Toolbar for the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        groupID = intent.getStringExtra("group_id");
        recipient_id = intent.getStringArrayListExtra("recipient_id");
        //TODO: getting location method is asynchronous. instead of manually adding change code appropriately.
        locations.add(12.9356511);
        locations.add(77.5347987);
        locations.add(12.9358166);
        locations.add(77.5347987);
        locations.add(12.9359949);
        locations.add(77.5348463);
        locations.add(12.926406);
        locations.add(77.583774);



        //members = (ArrayList<String>) group.get("members2");

        //members.addAll((ArrayList)group.getList("members2"));

        //members=group.getJSONArray("members2");
       /* if(members!=null) {
            for(int i=0; i<members.length(); i++) {
                try {
                    members2.add(members.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }*/
        //members = intent.getStringArrayListExtra("mems");
        // obj.put("members", recipient_id);
        // Toast.makeText(getApplicationContext(), members.toString(), Toast.LENGTH_LONG).show();

        //for (int i = 0; i < recipient_id.size(); i++) {
        // ParseQuery<ParseUser> query = ParseUser.getQuery();
        //query.whereEqualTo("objectId", recipient_id.get(i));
        //obj.put("objID", recipient_id.get(i));
        //final int j = i;
            /*query.getInBackground("objectId", new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        // row of Object Id "U8mCwTHOaC"
                        //ParseObject p = object.get(j);
                        point = (ParseGeoPoint) object.get("location");
                        obj.put("locn", point);
                        if (point != null) {
                            lat = point.getLatitude();
                            longi = point.getLongitude();
                            locations.add(lat);
                            locations.add(longi);
                        }
                    } else {
                        obj.put("Error", "error");
                    }
                }
            });

            query.getFirstInBackground(recipient_id.get(i), new GetCallback<ParseUser>() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if(e==null) {
                        lat = (double) parseUser.get("latitude");
                        longi = (double) parseUser.get("longitude");
                        locations.add(lat);
                        locations.add(longi);

                        Toast.makeText(getApplicationContext(), locations.toString(), Toast.LENGTH_LONG).show();
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
            });
*//*            Log.e("recipient_id", recipient_id.get(i)+"");

            query.getInBackground(recipient_id.get(i), new GetCallback<ParseUser>() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null) {
                        lat = (double) parseUser.get("latitude");
                        longi = (double) parseUser.get("longitude");
                        locations.add(lat);
                        locations.add(longi);

                        Toast.makeText(getApplicationContext(), locations.toString(), Toast.LENGTH_LONG).show();
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
            });

            Log.e("recipient_id", recipient_id.get(i)+"");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
            query.whereEqualTo("Username", recipient_id.get(i));
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if(object==null) {

                        //Toast.makeText(getApplicationContext(), locations.toString(), Toast.LENGTH_LONG).show();
                    }
                    else{
                        //Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        //e.printStackTrace();
                        lat = (double) object.get("latitude");
                        longi = (double) object.get("longitude");
                        Log.e("Latitude", lat+"");
                        locations.add(lat);
                        locations.add(longi);
                        Log.e("Locations", locations+"");
                    }
                }
            });
        }

*/
        Log.e("Locations", locations + "");
        //obj.put("locations", locations);
        //obj.saveInBackground();


        ArrayList<Double> locations2 = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            locations2.add(i, locations.get(i) * PI / 180);
        }
        Log.e("Locations2", locations2+"");
        ArrayList<Double> co_ords = new ArrayList<>();

        //int sizeOfCoOrds = locations2.size() * 3 / 2;

        for (int i = 0; i < locations2.size(); i += 2) {
            co_ords.add(Math.cos(locations2.get(i)) * Math.cos(locations2.get(i + 1)));
            co_ords.add(Math.cos(locations2.get(i)) * Math.sin(locations2.get(i + 1)));
            co_ords.add(Math.sin(locations2.get(i)));
        }
        obj.put("Co ords", co_ords);
        Log.e("co_ords", co_ords.size() + "");
        Log.e("Co-Ords", co_ords+"");

        totWeight = co_ords.size() / 3;
        Log.e("Weight", totWeight + "");
        //obj.put("weight", totWeight);
        for (int i = 0; i < totWeight; i++) {
            int j = 0;

            while ((j + 3) < co_ords.size()) {
                sum += (co_ords.get(i + j) / totWeight);
                j += 3;
            }

            finalList.add(sum);
            sum = 0.0;
        }

        Log.e("final List", finalList+"");
        //Toast.makeText(getApplicationContext(), finalList.toString(), Toast.LENGTH_LONG).show();
        //obj.put("finalList", finalList);
        //obj.saveInBackground();


        double longitude = Math.atan2(finalList.get(1), finalList.get(0));

        double hyp = Math.sqrt(finalList.get(0) * finalList.get(0) + finalList.get(1) * finalList.get(1));

        double lattitude = Math.atan2(finalList.get(2), hyp);

        double midLat = lattitude*(180/PI);
        double midLong = longitude*(180/PI);

        final ParseGeoPoint p2 = new ParseGeoPoint(midLat, midLong);
        Log.e("p2", p2+"");
        // ParseObject places = new ParseObject("Places");

        ParseQuery<ParseObject> placs = ParseQuery.getQuery("Places");
        Log.e("PLacs", placs + "");
        placs.whereNotEqualTo("location", null);
        placs.findInBackground(new FindCallback<ParseObject>() {
            @Override
            synchronized public void done(List<ParseObject> list, ParseException e) {
                Log.e("LIST", list + "");
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        ParseObject p = list.get(i);
                        Log.e("PARSE p", p + "");
                        ParseGeoPoint place = (ParseGeoPoint) p.get("location");
                        String name = (String) p.get("Name");
                        //distances.add(place.distanceInKilometersTo(p2));
                        map.put(name, place.distanceInKilometersTo(p2));
                    }
                    // Collections.sort(distances);
                    // Log.e("Distances", distances + "");
                    // Toast.makeText(getApplicationContext(), distances.toString(), Toast.LENGTH_LONG).show();
                    Set<Map.Entry<String, Double>> set = map.entrySet();
                    List<Map.Entry<String, Double>> list2 = new ArrayList<Map.Entry<String, Double>>(set);
                    Collections.sort( list2, new Comparator<Map.Entry<String, Double>>() {
                        public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 ) {
                            return (o2.getValue()).compareTo( o1.getValue() );
                        }
                    } );

                    int i =0;
                    for(Map.Entry<String, Double> entry:list2) {
                        Toast.makeText(getApplicationContext(), "Best place to visit: " +entry.getKey(), Toast.LENGTH_LONG).show();
                        if(i==0)
                            break;
                    }

                } else Log.e("Error", "Error");
            }

        });
        //while(distances==null) {}





        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View view) {
        //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //            .setAction("Action", null).show();
        //}
        //});
    }

}
