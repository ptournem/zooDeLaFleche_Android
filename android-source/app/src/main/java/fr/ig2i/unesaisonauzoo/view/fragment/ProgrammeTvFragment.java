package fr.ig2i.unesaisonauzoo.view.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fr.ig2i.unesaisonauzoo.R;
import fr.ig2i.unesaisonauzoo.adapter.ProgrammeTVAdapter;
import fr.ig2i.unesaisonauzoo.model.UneSaisonAuZooApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgrammeTvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgrammeTvFragment extends Fragment {
    ListView listeProgramme;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProgrammeTvFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgrammeTvFragment newInstance() {
        ProgrammeTvFragment fragment = new ProgrammeTvFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProgrammeTvFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_programme_tv, container, false);
        }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // récupération des views
        listeProgramme = (ListView) getActivity().findViewById(R.id.LVProgrammeTV);

        // création de l'adapter
        ProgrammeTVAdapter adapter = new ProgrammeTVAdapter(getActivity().getBaseContext(),(UneSaisonAuZooApplication) getActivity().getApplication());
        // ajout de l'adapter
        listeProgramme.setAdapter(adapter);
    }
}
