package com.geekscode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Betty Vulule on 26/02/2018.
 */

public class TimeTableListFragment extends Fragment implements SearchView.OnQueryTextListener {

    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    String[] courseList;
    ArrayList<CourseList> arraylist = new ArrayList<>();
    SharedPreferences shared;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetable_list, container, false);

        shared = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = shared.edit();

        buildCodeList();

        list = view.findViewById(R.id.listview);

        for (int i = 0; i < courseList.length; i++) {
            CourseList list = new CourseList(courseList[i]);
          // Binds all strings into an array
            arraylist.add(list);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(getContext(), arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = view.findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
        return view;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    //build string array by fetching all key values in our sharedPreference
    private void buildCodeList(){
        Map<String,?> keys = shared.getAll();
        courseList = new String[keys.size()];
        int i = 0;
        for(Map.Entry<String,?> entry : keys.entrySet()){
            if(courseList!=null)
            courseList[i] = entry.getKey();
            i++;
        }
    }


    //refresh our timetable list once the fragment is opened/visible
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
         refreshTimeTableList();
        }

    }

    //refresh timetable data list and notify adapter of changes
    public void refreshTimeTableList(){
        arraylist.clear();
        buildCodeList();
        for (int i = 0; i < courseList.length; i++) {
            // Binds all strings into an array
            arraylist.add(new CourseList(courseList[i]));
        }
        adapter.notifyDataSetChanged();
    }




    //Our adapter class comes here
    public class ListViewAdapter extends BaseAdapter {

        String courseText = "Course Code : ";
        Context mContext;
        LayoutInflater inflater;
        private List<CourseList> courseLists = null;
        private ArrayList<CourseList> arraylist;
        SharedPreferences shared;
        SharedPreferences.Editor editor;

        public ListViewAdapter(Context context, List<CourseList> courseCodeList) {
            mContext = context;
            this.courseLists = courseCodeList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<>();
            this.arraylist.addAll(courseCodeList);
        }

        public class ViewHolder {
            TextView name;
            CardView cardView;
            ImageButton removeBtn;
        }

        @Override
        public int getCount() {
            return courseLists.size();
        }

        @Override
        public CourseList getItem(int position) {
            return courseLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.item_card, null);
                // Locate the TextViews in listview_item.xml
                holder.name = view.findViewById(R.id.title_text);
                holder.cardView = view.findViewById(R.id.card_view);
                holder.removeBtn = view.findViewById(R.id.remove);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            holder.name.setText(courseText + courseLists.get(position).getCourseCode());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String courseCode = holder.name.getText().toString().replaceAll(courseText, "");
                    Intent intent = new Intent(v.getContext(), EditTimeTableActivity.class);
                    intent.putExtra("coursecode", courseCode);
                    v.getContext().startActivity(intent);
                }
            });

            shared = view.getContext().getSharedPreferences(view.getContext().getString(R.string.app_name), Context.MODE_PRIVATE);
            editor = shared.edit();

            holder.removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String courseCode = holder.name.getText().toString().replaceAll(courseText, "");
                    editor.remove(courseCode);
                    editor.commit();
                    refreshTimeTableList();
                    //refreshList();
                    Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_LONG).show();
                }
            });
            return view;
        }

        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            courseLists.clear();
            if (charText.length() == 0) {
                courseLists.addAll(arraylist);
            } else {
                for (CourseList cl : arraylist) {
                    if (cl.getCourseCode().toLowerCase(Locale.getDefault()).contains(charText)) {
                        courseLists.add(cl);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

}
