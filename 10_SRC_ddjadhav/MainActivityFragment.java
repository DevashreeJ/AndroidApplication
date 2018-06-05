package com.example.devas.app_10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by devas on 9/18/2017.
 */

public class MainActivityFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private  ButtonClickInterface buttonClickInterfacevar;
    Button aboutme ;
    Button recyclerView;
    Button task3;
    public static MainActivityFragment newInstance(int sectionNumber) {
        MainActivityFragment mainPage = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        mainPage.setArguments(args);
        return mainPage;
    }

    public MainActivityFragment() {

    }
    public interface ButtonClickInterface {
        public void onButtonClickListener(View v);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {

        final View view = inflater.inflate(R.layout.main_activity_fragment,container,false);
        aboutme = (Button)view.findViewById(R.id.aboutme);
        recyclerView = (Button)view.findViewById(R.id.task1);
        task3=(Button)view.findViewById(R.id.bottomToolbar);


        recyclerView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public  void onClick(View v)
            {
                Intent intent= new Intent(getActivity(),RecyclerViewClass.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        v,"fragmentToActivity");
                getActivity().startActivity(intent,optionsCompat.toBundle());
            }

        });
        aboutme.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                buttonClicked(v);
            }
        });

        task3.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public  void onClick(View v)
            {
                Intent intent= new Intent(getActivity(),SecondActivity.class);
                startActivity(intent);
            }

        });
        return view;
    }

    public void  buttonClicked(View v){
        buttonClickInterfacevar.onButtonClickListener(v);
    }

    @Override
    @Deprecated
    public void onAttach ( Activity activity ) {
        super . onAttach ( activity );
        buttonClickInterfacevar = (ButtonClickInterface) activity ;
    }


}
