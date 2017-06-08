package com.jing.www.expandableview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener,  ExpandableListView.OnGroupExpandListener {
    ExpandableListView mainlistview = null;
    List<String> parent = null;
    Map<String, List<String>> map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        mainlistview = (ExpandableListView) this
                .findViewById(R.id.main_expandablelistview);
        // 初始化数据
        initData();
        //给expandListView添加适配器
        mainlistview.setAdapter(new MyAdapter());
       //几种监听器
        /*--------------1.组的监听-------------*/
        mainlistview.setOnGroupClickListener(this);
        /*--------------2.子条目的监听-------------*/
        mainlistview.setOnChildClickListener(this);
//        /*--------------3.item的点击事件,是按钮点击事件?-------------*/
        mainlistview.setOnItemClickListener(this);
        //将默认的箭头去除
        mainlistview.setGroupIndicator(null);
//        //添加折叠的监听
//        mainlistview.setOnGroupCollapseListener(this);
        //添加展开的监听
        mainlistview.setOnGroupExpandListener(this);


    }

    // 初始化数据
    public void initData() {
        parent = new ArrayList<String>();
        parent.add("parent1");
        parent.add("parent2");
        parent.add("parent3");

        //对应的键是父亲,对应的值是存放孩子的集合
        map = new HashMap<String, List<String>>();

        List<String> list1 = new ArrayList<String>();
        list1.add("child1-1");
        list1.add("child1-2");
        list1.add("child1-3");
        map.put("parent1", list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("child2-1");
        list2.add("child2-2");
        list2.add("child2-3");
        map.put("parent2", list2);

        List<String> list3 = new ArrayList<String>();
        list3.add("child3-1");
        list3.add("child3-2");
        list3.add("child3-3");
        map.put("parent3", list3);

    }


    /*--------------1.组条目的点击事件-------------*/
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        Toast.makeText(this, "点击父条目", Toast.LENGTH_SHORT).show();
        return false;
    }

    /*--------------2.子条目的点击事件-------------*/
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(this, "点击孩子条目", Toast.LENGTH_SHORT).show();
        return false;
    }

    /*-------------3.item的点击事件是--------------*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "点击item条目", Toast.LENGTH_SHORT).show();
    }

    /*--------------4.添加折叠的事件监听-------------*/
//    @Override
//    public void onGroupCollapse(int groupPosition) {
//        for (int i = 0; i < parent.size()+1; i++) {
//            if (groupPosition != i) {
//                Toast.makeText(this, groupPosition+"", Toast.LENGTH_SHORT).show();
//                mainlistview.collapseGroup(i);
//
//            }
//        }
//    }

    @Override
    public void onGroupExpand(int groupPosition) {
        for (int i = 0; i < parent.size()+1; i++) {
            if (groupPosition != i) {

                mainlistview.collapseGroup(i);
            }
            }
    }


    class MyAdapter extends BaseExpandableListAdapter {



        /*--------------获取组的总数-------------*/
        @Override
        public int getGroupCount() {
            return parent.size();
        }

        /*--------------获取每组中子条目的总数,即当前组的孩子总数-------------*/
        @Override
        public int getChildrenCount(int groupPosition) {
            String s = parent.get(groupPosition); //得到当前组的名称
            int size = map.get(s).size();//通过组名称获取当前的子条目集合
            return size;
        }

        /*--------------得到各组的数据-------------*/
        @Override
        public Object getGroup(int groupPosition) {
            return parent.get(groupPosition);
        }

        /*--------------得到各组孩子的数据-------------*/
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String s = parent.get(groupPosition); //得到当前组的名称
            String s1 = map.get(s).get(childPosition);
            return s1;
        }

        /*--------------得到当前组的id-------------*/
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        /*--------------得到当前组当前孩子的id-------------*/
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        /*--------------得到当前组的view,与ListView的得到view的写法类似,需要用inflater来进行响应的渲染-------------*/
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null){
                //获取打气筒,LayoutInflater
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_parent, null);
            }
            TextView tv_parent = (TextView) convertView.findViewById(R.id.parent_textview);
          //  tv_parent.setText(MainActivity.this.parent.get(groupPosition));
             tv_parent.setText((String)getGroup(groupPosition));
            return convertView;
        }

        /*--------------得到当前组当前孩子的view,与ListView的得到view的写法类似,需要用inflater来进行相应的渲染-------------*/
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null){
                //获取打气筒,LayoutInflater
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_child, null);
            }
            TextView tv_parent = (TextView) convertView.findViewById(R.id.second_textview);
//            String s = MainActivity.this.parent.get(groupPosition);
//            String s1 = map.get(s).get(childPosition);
//            tv_parent.setText(s1);
            tv_parent.setText((String)getChild(groupPosition,childPosition));

            return convertView;
        }
        @Override
        public boolean hasStableIds() {
            return true;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            /* 很重要：实现ChildView点击事件，必须返回true */
            return true;
        }

    }
}

