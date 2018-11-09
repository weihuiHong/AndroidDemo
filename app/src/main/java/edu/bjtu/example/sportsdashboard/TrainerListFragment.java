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


public class TrainerListFragment extends Fragment {
    String[] players = {"Wang Mo", "Li Jian", "刘教练", "David Zhang", "赵武 教练", "Van Persie", "Oscar"};
    String[] experience = {
            "Perfection is not attainable, but if we chase perfection we can catch excellence",
            "Health & Fitness Lifestyle Transformation.Gym doesn't change live, People do.",
            "If we chase perfection we can catch excellence",
            "Gym doesn't change live, People do.",
            "An accomplished fitness trainer with seven years of experience n hand",
            "Gym doesn't change live, People do.",
            "Gym doesn't change live, People do."
    };
    int[] images = {R.drawable.trainer1, R.drawable.trainer2, R.drawable.trainer3, R.drawable.trainer4, R.drawable.trainer5, R.drawable.trainer6, R.drawable.athelet_1};

    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    private SearchView sv;
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trainer_list, container, false);

        HashMap<String, String> map = new HashMap<String, String>();

        //FILL
        for (int i = 0; i < players.length; i++) {
            map = new HashMap<String, String>();
            map.put("Player", players[i]);
            map.put("Info",experience[i]);
            map.put("Image", Integer.toString(images[i]));

            data.add(map);
        }

        //KEYS IN MAP
        String[] from = {"Player","Info", "Image"};

        //IDS OF VIEWS
        int[] to = {R.id.nameTxt, R.id.infoTxt, R.id.imageView1};

        //ADAPTER
        adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.trainerlist_item, from, to);

        lv = (ListView)rootView.findViewById(R.id.trainer_list);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapter.getItem(i).toString();
                name = name.substring(adapter.getItem(i).toString().indexOf("Player=") + "Player=".length(), name.length() - 1);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Book trainer")
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

        sv = (SearchView)rootView.findViewById(R.id.trainer_btn);
        sv.setIconifiedByDefault(false);
        sv.setSubmitButtonEnabled(true);

        sv.setQueryHint("Search trainer");

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
