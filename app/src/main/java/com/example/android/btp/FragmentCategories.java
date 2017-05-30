package com.example.android.btp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Created by Sainadh Chilukamari on 7/14/2016.
 */
public class FragmentCategories extends ListFragment implements AdapterView.OnItemClickListener {

    public FragmentCategories() {
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
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public void fragmenter(){
        FragmentSeeds frag6 = new FragmentSeeds();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent,frag6,"SeedsCategories")
                .addToBackStack("SeedsCategories")
                .commit();
        getActivity().setTitle("Seeds");
        Toast.makeText(getActivity(), "Seeds", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position){
            case 0:
                FragmentAnimalFeedCategories frag0 = new FragmentAnimalFeedCategories();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag0,"Categories")
                        .addToBackStack("Categories")
                        .commit();
                getActivity().setTitle("AnimalFeed");
                Toast.makeText(getActivity(), "Cattle Feed", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                FragmentCattleFeed frag = new FragmentCattleFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag,"Categories")
                        .addToBackStack("Categories")
                        .commit();
                getActivity().setTitle("Cattle Feed");
                Toast.makeText(getActivity(), "Cattle Feed", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                FragmentAquaFeed frag1 = new FragmentAquaFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag1,"Categories")
                        .addToBackStack("Categories")
                        .commit();
                getActivity().setTitle("Aqua Feed");
                Toast.makeText(getActivity(), "Poultry Feed", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                FragmentAquaFeed frag2 = new FragmentAquaFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag2,"Categories")
                        .addToBackStack("Categories")
                        .commit();
                getActivity().setTitle("Aqua Feed");
                Toast.makeText(getActivity(), "Aqua Feed", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                FragmentSheepFeed frag3 = new FragmentSheepFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag3,"Categories")
                        .addToBackStack("Categories")
                        .commit();
                getActivity().setTitle("Sheep Feed");
                Toast.makeText(getActivity(), "Sheep and Goat Feed", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                FragmentSpecialityFeed frag4 = new FragmentSpecialityFeed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag4,"Categories")
                        .addToBackStack("Categories")
                        .commit();
                getActivity().setTitle("Speciality Feed");
                Toast.makeText(getActivity(), "Speciality Feed", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(getActivity(), "Fertilizers coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case 7:
                FragmentSeedsCategories frag5 = new FragmentSeedsCategories();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent,frag5,"Categories")
                        .addToBackStack("Categories")
                        .commit();
                getActivity().setTitle("Seeds");
                Toast.makeText(getActivity(), "Seeds", Toast.LENGTH_SHORT).show();
                break;
            case 8:
                fragmenter();
                break;
            case 9:
                fragmenter();
                break;
            case 10:
                fragmenter();
                break;
            case 11:
                fragmenter();
                break;
            case 12:
                fragmenter();
                break;
            case 13:
                Toast.makeText(getActivity(), "Pesticides coming soon!", Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
