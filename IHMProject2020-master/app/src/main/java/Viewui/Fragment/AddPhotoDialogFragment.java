package Viewui.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import Interface.IPhotoDialogListener;

import com.example.ihmproject.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPhotoDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPhotoDialogFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IPhotoDialogListener mCallBack;

    public AddPhotoDialogFragment() {
        // Required empty public constructor
    }
    public AddPhotoDialogFragment(IPhotoDialogListener mCallBack) {
        // Required empty public constructor
        this.mCallBack = mCallBack;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mCallBack = (IPhotoDialogListener) getActivity();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPhotoDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPhotoDialogFragment newInstance(String param1, String param2) {
        AddPhotoDialogFragment fragment = new AddPhotoDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = getThisView(inflater,container);
        return view;
    }

    public View getThisView(LayoutInflater inflater){
        return getThisView(inflater,null);
    }
    public View getThisView(LayoutInflater inflater,  ViewGroup container){
        View view = container != null?  inflater.inflate(R.layout.fragment_add_photo_dialog, container, false):
                inflater.inflate(R.layout.fragment_add_photo_dialog, null);
        ((ImageButton)view.findViewById(R.id.picture_from_camera)).setOnClickListener(this);
        ((ImageButton)view.findViewById(R.id.import_pictures)).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.picture_from_camera:
                mCallBack.onPhotoClik();
                break;
            case R.id.import_pictures:
                mCallBack.onImportPhotoClick();
                break;
        }
    }
}
