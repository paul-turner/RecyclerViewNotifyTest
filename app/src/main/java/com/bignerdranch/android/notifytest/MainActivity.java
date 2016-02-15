package com.bignerdranch.android.notifytest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mItems;
    private TextAdapter mTextAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mItems = new ArrayList<>(Arrays.asList("1111111", "BBBBBB", "CCCCCC", "DDDDDDD", "EEEEEEE"));
        mTextAdapter = new TextAdapter(this, mItems, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItems.size() > 2) {
                    mItems.add(1, "222222");
                    mTextAdapter.notifyItemInserted(1);
                    mItems.subList(2, 6).clear();
                    mTextAdapter.notifyItemRangeRemoved(2, 4);
                } else {
                    mItems.remove(1);
                    mItems.addAll(Arrays.asList("BBBBBB", "CCCCCC", "DDDDDDD", "EEEEEEE"));
                    mTextAdapter.notifyDataSetChanged();
                }
            }
        });
        recyclerView.setAdapter(mTextAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder> {

        private LayoutInflater mLayoutInflater;
        private List<String> mStringList;
        private View.OnClickListener mOnClickListener;

        public TextAdapter(Context context, List<String> stringList, View.OnClickListener onClickListener) {
            mLayoutInflater = LayoutInflater.from(context);;
            mStringList = stringList;
            mOnClickListener = onClickListener;
        }

        @Override
        public TextAdapter.TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(R.layout.text_item_view, parent, false);
            view.setOnClickListener(mOnClickListener);
            return new TextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TextAdapter.TextViewHolder holder, int position) {
            holder.bindText(mStringList.get(position));
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }


        public class TextViewHolder extends RecyclerView.ViewHolder {

            private TextView mTextView;

            public TextViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView;
            }

            public void bindText(String text) {
                mTextView.setText(text);
            }
        }
    }
}
