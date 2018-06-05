package com.example.devas.app_10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

//import android.widget.Toolbar;


/**
 * Created by devas on 9/19/2017.
 */

public class Movie_details_fragment extends Fragment{

        HashMap<String,?> movieData;
        //public static int position;
        Toolbar toolbar;
        ShareActionProvider shareActionProvider=new ShareActionProvider(getContext());

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            if(getArguments()!=null)
            {
                movieData = (HashMap<String, ?>)getArguments().getSerializable("movie");
            }
        }


        @Override
        public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {

            View rootView =inflater.inflate(R.layout.movie_details_fragment,container, false);
            final TextView name = (TextView)rootView.findViewById(R.id.textView12);
            final TextView overview = (TextView)rootView.findViewById(R.id.textView7);
            final TextView releaseDate = (TextView) rootView.findViewById(R.id.textView8);
            final TextView voteCount = (TextView)rootView.findViewById(R.id.textView13);
            final TextView popularity = (TextView)rootView.findViewById(R.id.textView14);
            final ImageView image = (ImageView)rootView.findViewById(R.id.imageView);
            if(getActivity().findViewById(R.id.activity_toolbar)!=null) {
                toolbar = (Toolbar) getActivity().findViewById(R.id.activity_toolbar);
                toolbar.setTitle(movieData.get("name").toString());
            }
            else {
                toolbar = (Toolbar) getActivity().findViewById(R.id.activity2_toolbar);
                toolbar.setTitle(movieData.get("name").toString());
            }

            name.setText(movieData.get("name").toString());
            overview.setText(movieData.get("overview").toString());
            releaseDate.setText(movieData.get("year").toString());
            voteCount.setText(movieData.get("vote_count").toString());
            popularity.setText(movieData.get("popularity").toString());
            image.setImageResource((Integer) movieData.get("image"));
            image.setTransitionName((String)movieData.get("name"));


            setHasOptionsMenu(true);
            setRetainInstance(true);


            return rootView;

        }


        public static Movie_details_fragment newInstance(HashMap<String,?> movieData) {
            Movie_details_fragment movie_details_fragment = new Movie_details_fragment();
            Bundle args = new Bundle();
            args.putSerializable("movie",movieData);
            movie_details_fragment.setArguments(args);
            return movie_details_fragment;
        }

        public Movie_details_fragment() {

        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
        {
            if(menu.findItem(R.id.fragment2_task1)==null) {
                menuInflater.inflate(R.menu.fragment2_menu_file, menu);
            }
            MenuItem shareInfo = menu.findItem(R.id.fragment2_task1);
            shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareInfo);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            //shareIntent.putExtra(Intent.EXTRA_TEXT,"in the fragment");
            shareIntent.putExtra(Intent.EXTRA_TEXT,(String)movieData.get("name"));
            shareActionProvider.setShareIntent(shareIntent);

            super.onCreateOptionsMenu(menu,menuInflater);
            //return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            int id = item.getItemId();
            // Intent intent;
            switch (id)
            {
                case R.id.fragment2_task1 :
                    Toast.makeText(getContext(), "fragment2_menu_item1 option clicked", Toast.LENGTH_LONG).show();
                    return  true;

                default:break;
            }
            return super.onOptionsItemSelected(item);
        }

}
