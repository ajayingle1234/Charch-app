package hardcastle.com.churchapplication.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.Adapter.FAQAdapter;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.FAQBean;


public class FAQsFragment extends Fragment {

    private List<FAQBean> faqBeanList;
    private RecyclerView recyclerView;
    private FAQAdapter adapter;
    private Toolbar toolbar;

    public FAQsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FAQsFragment newInstance() {
        FAQsFragment fragment = new FAQsFragment();

        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);
       /* String[] categories;
        categories = getResources().getStringArray(R.array.faqs_array);*/

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getActivity().setTitle("FAQ's");
        getActivity().setActionBar(toolbar);

        faqBeanList = new ArrayList<>();
        FAQBean faqBean1 = new FAQBean("Q.1 How Can I Change My Address?", getResources().getString(R.string.text_with_paragraphs));
        FAQBean faqBean2 = new FAQBean("Q.2 How Do I Activate My Account?", getResources().getString(R.string.text_with_paragraphs));
        FAQBean faqBean3 = new FAQBean("Q.3 What Do You Mean By Points?", getResources().getString(R.string.text_with_paragraphs));
        FAQBean faqBean4 = new FAQBean("Q.4 How Do I Earn It?", getResources().getString(R.string.text_with_paragraphs));
        FAQBean faqBean5 = new FAQBean("Q.5 Why Is There A Checkout Limit?", getResources().getString(R.string.text_with_paragraphs));
        FAQBean faqBean6 = new FAQBean("Q.6 What Are All The Checkout Limits?", getResources().getString(R.string.text_with_paragraphs));
        FAQBean faqBean7 = new FAQBean("Q.7 How Many Free Samples Can I Redeem?", getResources().getString(R.string.text_with_paragraphs));
        faqBeanList.add(faqBean1);
        faqBeanList.add(faqBean2);
        faqBeanList.add(faqBean3);
        faqBeanList.add(faqBean4);
        faqBeanList.add(faqBean5);
        faqBeanList.add(faqBean6);
        faqBeanList.add(faqBean7);

        recyclerView = view.findViewById(R.id.faq_recyclerView);

        adapter = new FAQAdapter(getActivity(), faqBeanList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
