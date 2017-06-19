package com.example.preorderlibrary.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.preorderlibrary.R;
import com.example.preorderlibrary.adapters.impleAdapter.QuestionListAdapter;

import java.util.List;

/**
 * Created by willyou on 2016/12/9.
 */

public class MyView extends LinearLayout {

    LinearLayout questionContentLayout;
    LinearLayout questionTitleLayout;
    List<QuestionListAdapter.CheckedQuestionData> questionDatas;

    private OnAnswerClickListener onAnswerClickListener;

    public static final int TYPE_SIGLE = 0;
    public static final int TYPE_MULTIPLE = 1;

    private int questionNum = 0;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void handleView(List<QuestionListAdapter.CheckedQuestionData> questionDatas){
        questionContentLayout.removeAllViews();
        questionTitleLayout.removeAllViews();

        final boolean isSingleChoice = !questionDatas.get(0).getQuestionData().getQuestionId().equals("多选题干");

        View viewTitle=View.inflate(getContext(),R.layout.question_title,null);

        TextView questionNumTv= (TextView) viewTitle.findViewById(R.id.questionNum);
        questionNumTv.setText((questionNum + 1) + ":");

        TextView questionContentTv= (TextView) viewTitle.findViewById(R.id.questionContent);
        questionContentTv.setText(questionDatas.get(0).getQuestionData().getQuestionContent());

        TextView multipleChoice = (TextView) viewTitle.findViewById(R.id.question_mcq);
        multipleChoice.setVisibility(isSingleChoice ? View.GONE : View.VISIBLE);

        questionTitleLayout.addView(viewTitle);

        for (int i=1;i<questionDatas.size();i++){
            View viewContent = View.inflate(getContext(),R.layout.question_content_layout,null);
            View checkBox = viewContent.findViewById(R.id.question_cbx);
            checkBox.setBackgroundResource(questionDatas.get(i).isChecked() ? R.drawable.btn_qu_s :
                    R.drawable.btn_qu_n);
            TextView contentID = (TextView) viewContent.findViewById(R.id.content_ID);
            TextView content = (TextView) viewContent.findViewById(R.id.content);
            contentID.setText((char) (96 + i) + ".");
            content.setText(questionDatas.get(i).getQuestionData().getQuestionContent());
            questionContentLayout.addView(viewContent);

            final int index = i;
            View answerItem = viewContent.findViewById(R.id.ll_answer_item);
            answerItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAnswerClickListener != null)
                        onAnswerClickListener.onAnswerItemClickListener(index, isSingleChoice ?
                                TYPE_SIGLE : TYPE_MULTIPLE);
                }
            });
        }
    }

//    /**
//     * 单选MyView中 某个编号为index的答案 check为true即选中
//     * @param index 答案view在回答layout中的位置
//     * @param check 是否选中
//     */
//    public void singleChoice(int index, boolean check) {
//        int childCount = questionContentLayout.getChildCount();
//        if (childCount <= index) return;
//        for (int i = 0; i < childCount; i++) {
//            LinearLayout parent = (LinearLayout) questionContentLayout.getChildAt(i);
//            if (parent != null) {
//                View checkBox = parent.findViewById(R.id.question_cbx);
//                checkBox.setBackgroundResource(R.drawable.btn_yes_n);
//            }
//        }
//        multipleChoice(index, check);
//    }
//
//    public void multipleChoice(int index, boolean check) {
//        if (questionContentLayout.getChildCount() <= index) return;
//        questionContentLayout.getChildAt(index)
//                .setBackgroundResource(check ? R.drawable.btn_yes_n : R.drawable.btn_yes_s);
//    }


    public void addData(List<QuestionListAdapter.CheckedQuestionData> list, int num) {
        this.questionDatas = list;
        this.questionNum = num;
        handleView(questionDatas);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.question_title_layout,this);
        questionContentLayout= (LinearLayout)findViewById(R.id.content_layout);
        questionTitleLayout= (LinearLayout) findViewById(R.id.title_layout);
    }

    public void setOnAnswerClickListener(OnAnswerClickListener onAnswerClickListener) {
        this.onAnswerClickListener = onAnswerClickListener;
    }

    public interface OnAnswerClickListener {

        void onAnswerItemClickListener(int index, int type);
    }

}
