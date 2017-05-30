package com.example.android.btp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAnimalFeedCategories extends ListFragment implements AdapterView.OnItemClickListener {


    public FragmentAnimalFeedCategories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Animal_Feed, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        switch(position){
            case 0:
                FragmentCattleFeed frag = new FragmentCattleFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag,"Categories")
                        .addToBackStack("Categories")
                        .commit();

                Toast.makeText(getActivity(), "Cattle Feed", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                FragmentAquaFeed frag1 = new FragmentAquaFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag1,"Categories")
                        .addToBackStack("Categories")
                        .commit();

                Toast.makeText(getActivity(), "Poultry Feed", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                FragmentAquaFeed frag2 = new FragmentAquaFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag2,"Categories")
                        .addToBackStack("Categories")
                        .commit();

                Toast.makeText(getActivity(), "Aqua Feed", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                FragmentSheepFeed frag3 = new FragmentSheepFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag3,"Categories")
                        .addToBackStack("Categories")
                        .commit();

                Toast.makeText(getActivity(), "Sheep and Goat Feed", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                FragmentSpecialityFeed frag4 = new FragmentSpecialityFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag4,"Categories")
                        .addToBackStack("Categories")
                        .commit();

                Toast.makeText(getActivity(), "Speciality Feed", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
