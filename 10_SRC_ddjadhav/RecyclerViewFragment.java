package com.example.devas.app_10;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import android.widget.Toolbar;

/**
 * Created by devas on 9/28/2017.
 */

public class RecyclerViewFragment extends Fragment  {

    RecyclerView recycler;
    static MovieData movieData ;
    OnListItemSelectedListener mListener;
    Toolbar toolbar;
    Toolbar toolbar2;
    Button selectAllButton;
    Button clearSelected;
    private Context context;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static  RecyclerAdapter recyclerViewAdapter;
    public static RecyclerViewFragment newInstance(int sectionNumber) {
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        recyclerViewFragment.setArguments(args);
        return recyclerViewFragment;
    }

    public RecyclerViewFragment()
    {}
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        final View rootView = inflater.inflate(R.layout.recycler_view_layout, container, false);
        context = getActivity();

        selectAllButton = (Button)rootView.findViewById(R.id.selectAllButton);
        clearSelected=(Button)rootView.findViewById(R.id.clearAllButton);

        if(getActivity().findViewById(R.id.activity_toolbar)!=null) {
            toolbar = (Toolbar) getActivity().findViewById(R.id.activity_toolbar);
            toolbar.setTitle("Movie List");
        }
        else
        {
            toolbar2 = (Toolbar) getActivity().findViewById(R.id.activity2_toolbar);
            toolbar2.setTitle("Movie List");
        }
        recycler = (RecyclerView)rootView.findViewById(R.id.cardlist);
        recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(manager);

        setHasOptionsMenu(true);
        setRetainInstance(true);


        movieData = new MovieData();
        recyclerViewAdapter = new RecyclerAdapter(getActivity(),movieData.getMoviesList());


        recycler.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                                                       @Override
                                                       public void itemClick(View v, int position) {

                                                           HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getMoviesList().get(position);
                                                           mListener.OnListItemSelected(v, movie);

                                                       }

                                                       @Override
                                                       public void onItemLongClick(View view, int position) {
                                                           getActivity().startActionMode(new ToolbarManagerClass(position));
                                                           }

                                                       @Override
                                                       public void onCheckBoxClick(View view, int position) {
                                                           HashMap<String, Boolean> item = (HashMap<String, Boolean>) movieData.getItem(position);
                                                           boolean checked = item.get("selection");
                                                           if (checked == true) {
                                                               item.put("selection", false);
                                                           } else if (checked == false) {
                                                               item.put("selection", true);
                                                           }
                                                       }

                                                       @Override
                                                       public void onMenuClick(View view, final int position) {
                                                           PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                                                           popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                               @Override
                                                               public boolean onMenuItemClick(MenuItem menuItem) {
                                                                   switch (menuItem.getItemId()) {
                                                                       case R.id.delete_option:
                                                                           movieData.removeItem(position);
                                                                           recyclerViewAdapter.notifyItemRemoved(position);

                                                                           return true;

                                                                       case R.id.duplicate_option:
                                                                           movieData.addItem(position+1, (HashMap) ((HashMap) movieData.getItem(position)).clone());
                                                                           recyclerViewAdapter.notifyItemInserted(position+1);

                                                                           return true;

                                                                       default:
                                                                           return false;
                                                                   }
                                                               }
                                                           });

                                                           MenuInflater menuInflater = popupMenu.getMenuInflater();
                                                           menuInflater.inflate(R.menu.contextual_menu, popupMenu.getMenu());
                                                           popupMenu.show();
                                                       }

            @Override
            public void onImageClick(View view, int position) {
                HashMap<String, ?> movie = (HashMap<String,?>)movieData.getItem(position);
                mListener.OnListItemImageSelected(view,movie,position);            }


        });


        selectAllButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View v)
            {
                List<Map<String,?>> movie = movieData.getMoviesList();
                for(int i =0 ; i<recyclerViewAdapter.getItemCount();i++)
                {
                    HashMap<String, Boolean> selected = (HashMap<String, Boolean>)movieData.getItem(i);
                    selected.put("selection",true);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

        });

        clearSelected.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View v)
            {
                List<Map<String,?>> movie = movieData.getMoviesList();
                for(int i =0 ; i<recyclerViewAdapter.getItemCount();i++)
                {
                    HashMap<String, Boolean> selected = (HashMap<String, Boolean>)movieData.getItem(i);
                    selected.put("selection",false);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

        });


        return rootView;
    }

   /* public void openMovieDetailsFragment(int adapterPosition, View viewById) {
        if(context instanceof RecyclerViewClass)
        {
            HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getMoviesList().get(adapterPosition);
            Movie_details_fragment movieDetailsFragment = new Movie_details_fragment();
            Bundle bundle = new Bundle();
            bundle.putString("transitionName","transition"+adapterPosition);
            bundle.putSerializable("movie",movie);
            movieDetailsFragment.setArguments(bundle);
            ((RecyclerViewClass)context).showFragmentWithTransition(this,movieDetailsFragment,"movieDetail"
                    ,viewById,"transition"+adapterPosition);
        }
    }*/

    public interface OnListItemSelectedListener{
        public void OnListItemSelected(View v, HashMap<String, ?> movie);
        public void OnListItemImageSelected(View v,HashMap<String,?> movie,int position );
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.fragment_task2 :
                Toast.makeText(getContext(), "fragment menu item clicked", Toast.LENGTH_LONG).show();
                return  true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        if (menu.findItem(R.id.search)==null)
            inflater.inflate (R.menu.fragment_menu, menu);
        SearchView search = (SearchView)menu.findItem(R.id.search).getActionView();
        if(search != null)
        {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String query)
                {
                   int position = movieData.findName(query);
                    if(position>=0)
                        recycler.scrollToPosition(position);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String query)
                {
                    return true;}
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static void sortByName()
    {


                Collections.sort(movieData.getMoviesList(), new Comparator<Map<String, ?>>() {
                    @Override
                    public int compare(Map<String, ?> o1, Map<String, ?> o2) {
                return o1.get("name").toString().compareTo(o2.get("name").toString());
            }

        });
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public static void sortByYear()
    {

        Collections.sort(movieData.getMoviesList(), new Comparator<Map<String, ?>>() {
            @Override
            public int compare(Map<String, ?> o1, Map<String, ?> o2) {
                return o2.get("year").toString().compareTo(o1.get("year").toString());
            }
        });
        recyclerViewAdapter.notifyDataSetChanged();

    }

    public class ToolbarManagerClass implements android.view.ActionMode.Callback {
       int position;


        public ToolbarManagerClass(int position)
        {
            this.position = position;
        }

        @Override
        public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {

            HashMap movie = movieData.getItem(position);
            mode.setTitle(movie.get("name").toString());
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            switch(id)
            {
                case R.id.delete_option :
                    movieData.removeItem(position);
                    recyclerViewAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;

                case R.id.duplicate_option :
                    movieData.addItem(position+1, (HashMap)((HashMap) movieData.getItem(position)).clone());
                    recyclerViewAdapter.notifyItemInserted(position+1);
                    mode.finish();
                    break;

                default:
                    break;

            }
            return false;
        }

        @Override
        public void onDestroyActionMode(android.view.ActionMode mode) {

        }
    }


    @Override
    @Deprecated
    public void onAttach ( Activity activity ) {
        super . onAttach ( activity );

        mListener = ( OnListItemSelectedListener ) activity ;
    }

}
