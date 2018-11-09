package edu.bjtu.example.sportsdashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class ClassListFragment extends Fragment {
    String[] className = {"Artificial Intelligence", "Computer Architecture", "Embedded Systems", "Operating Systems", "Web Technologies", "Library Time", "Sports Time"};
    String[] time = {
            "Mon. 8:45-9:30",
            "Tue. 10:15-11:00",
            "Thu. 1:15-2:00",
            "Sat. 2:45-3:30",
            "Fri. 12:30-1:15",
            "Web. 11:00-11:45",
            "Thu 2:45-3:30"
    };

    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    private SearchView sv;
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.class_list, container, false);

        HashMap<String, String> map = new HashMap<String, String>();

        //FILL
        for (int i = 0; i < className.length; i++) {
            map = new HashMap<String, String>();
            map.put("Player", className[i]);
            map.put("Info",time[i]);

            data.add(map);
        }

        //KEYS IN MAP
        String[] from = {"Player","Info"};

        //IDS OF VIEWS
        int[] to = {R.id.nameTxt, R.id.infoTxt};

        //ADAPTER
        adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.classlist_item, from, to);

        lv = (ListView)rootView.findViewById(R.id.class_list);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapter.getItem(i).toString();
                name = name.substring(adapter.getItem(i).toString().indexOf("Player=") + "Player=".length(), name.length() - 1);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Book Class")
                        .setMessage("Do you want to book " + name + "?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.out.println("Yes");
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.out.println("No");
                            }
                        });
                builder.create().show();
            }
        });

        sv = (SearchView)rootView.findViewById(R.id.class_btn);
        sv.setIconifiedByDefault(false);
        sv.setSubmitButtonEnabled(true);

        sv.setQueryHint("Search class");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        return rootView;
    }
}
