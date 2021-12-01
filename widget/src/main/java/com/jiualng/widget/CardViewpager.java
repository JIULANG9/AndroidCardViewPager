package com.jiualng.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.jiualng.widget.interfac.CardViewpagerTabClickListener;
import com.jiualng.widget.utlis.JLDisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class CardViewpager extends CardView {

    private Context mContext;
    private int mWidth, mHeight = 36;//宽高
    /**
     * 画底部卡片的矩形
     */
    private RectF bottomCardRectF;
    private int bottomCordRound = 16;//底部卡片圆角
    private int lowerHeight = 12;//底层高度差


    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 画底画笔
     */
    private Paint bottomPaint;
    /**
     * 画卡片的矩形
     */
    private RectF cardRectF;
    /**
     * 顶部圆角
     */
    private int mRound = 6;

    /**
     * 文本
     */
    private float textWidth;
    private float textHeight = 100;
    private Paint textPaint;//文本笔画
    private int tabTextColor;//文本颜色
    private int tabSelectTextColor;//文本选中颜色
    private float tabTextSize = 16;//文本大小
    private int tabTextStyle;//文本风格

    private int arcControlX = 60;//值越大，弧度越大
    private int arcControlY = 6;
    private int arcWidth = 90;//曲线的宽度

    private int centerX;
    private int centerY;
    /**
     * 背景颜色
     */
    private int bottomCardColor;
    private int topCardColor;

    /**
     * 标签文本
     */
    private List<String> tabTextList;
    private int tabNumber;//标签数量
    /**
     * 选中的位置
     */
    private int tabPosition = 1;
    private ViewPager2 mViewPager2 = null;
    private ViewPager mViewPager = null;

    //标签点击监听
    public CardViewpagerTabClickListener onTabClickListener;

    public CardViewpager(Context context) {
        super(context);

    }

    public CardViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        init();
        initViewPager2();
        setDefaultData();
    }
    public CardViewpager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs, defStyleAttr);
    }


    private void init() {
        setBackground(getResources().getDrawable(R.drawable.bg_transparent));
    }

    private void initViewPager2() {
        View view = (View) View.inflate(mContext, R.layout.viewpager_layout, this);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2);
        setViewPager2(viewPager2);
    }



    /**
     * 默认数据
     */
    private void setDefaultData() {

        //底部卡片
        bottomPaint = new Paint();
        bottomPaint.setAntiAlias(true);
        bottomCardColor = Color.parseColor("#f0f1f5");//底层默认黑色
        bottomPaint.setColor(bottomCardColor);

        bottomCardRectF = new RectF();


        //外层卡片
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        topCardColor = Color.WHITE;//顶层卡片颜色 默认白色
        mPaint.setColor(topCardColor);


        cardRectF = new RectF();


        tabTextList = new ArrayList<>();
        tabTextList.add("tab一");
        tabTextList.add("tab二");
        tabTextList.add("tab三");
        tabNumber = tabTextList.size();

        /**
         * TAB文本
         */
        tabTextStyle = Typeface.NORMAL;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tabTextColor = Color.GRAY;//文本颜色
        tabSelectTextColor = Color.BLACK;//文本选中颜色
        textPaint.setColor(tabTextColor);
        textPaint.setTextSize(JLDisplayUtil.spToPx(mContext, tabTextSize));

        textPaint.setStyle(Paint.Style.FILL);

    }


    private void initAttr(AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CardViewpager, defStyleAttr, 0);

        int indexCount = typedArray.getIndexCount();

        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.CardViewpager_arcControlX) {
                arcControlX = typedArray.getInt(index, arcControlX);
            } else if (index == R.styleable.CardViewpager_bottomCardColor) {
                bottomCardColor = typedArray.getColor(index, bottomCardColor);
            } else if (index == R.styleable.CardViewpager_topCardColor) {
                topCardColor = typedArray.getColor(index, topCardColor);
            } else if (index == R.styleable.CardViewpager_tabTextColor) {
                tabTextColor = typedArray.getColor(index, tabTextColor);
            } else if (index == R.styleable.CardViewpager_tabSelectTextColor) {
                tabSelectTextColor = typedArray.getColor(index, tabSelectTextColor);
            } else if (index == R.styleable.CardViewpager_tabTextSize) {
                tabTextSize = typedArray.getDimension(index, tabTextSize);
            }
        }
        typedArray.recycle();
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bottomCardRectF.set(0, lowerHeight, mWidth, mHeight / 2);//底层层矩形(描边层)
        canvas.drawRoundRect(bottomCardRectF, bottomCordRound, bottomCordRound, bottomPaint);//画底层圆角矩形

        if (tabPosition == 0) {
            //最左边的图形
            Path pathLeft = new Path();
            pathLeft.lineTo(textWidth, 0);
            pathLeft.cubicTo(
                    textWidth + arcControlX, arcControlY,
                    textWidth + arcWidth - arcControlX,
                    textHeight - arcControlY,
                    textWidth + arcWidth,
                    textHeight);

            pathLeft.lineTo(mWidth, textHeight);
            pathLeft.lineTo(mWidth, mHeight);
            pathLeft.lineTo(0, mHeight);
            mPaint.setShadowLayer(1, 0, 2, Color.GRAY);
            canvas.drawPath(pathLeft, mPaint);
        } else if (tabPosition == tabNumber - 1) {
            //最右边的图形
            Path pathLeft = new Path();

            pathLeft.lineTo(0, textHeight);
            pathLeft.lineTo(mWidth - textWidth - arcWidth, textHeight);
            pathLeft.cubicTo(
                    mWidth - textWidth - arcWidth + arcControlX, textHeight - arcControlY,
                    mWidth - textWidth - arcControlX,
                    arcControlY,
                    mWidth - textWidth, 0);
            pathLeft.lineTo(mWidth, 0);
            pathLeft.lineTo(mWidth, mHeight);
            pathLeft.lineTo(0, mHeight);
            mPaint.setShadowLayer(1, 6, 2, Color.GRAY);
            canvas.drawPath(pathLeft, mPaint);

        } else {
            //中间图形
            Path pathLeft = new Path();
            pathLeft.lineTo(0, textHeight);

            float start_location = textWidth * tabPosition + arcWidth * (tabPosition - 1);

            pathLeft.lineTo(start_location, textHeight);
            //左边圆角
            pathLeft.cubicTo(
                    start_location + arcControlX, textHeight - arcControlY,
                    start_location + arcWidth - arcControlX, arcControlY,
                    start_location + arcWidth, 0);
            pathLeft.lineTo(start_location + arcWidth + textWidth, 0);

            //右边圆角
            float ent_location = start_location + arcWidth + textWidth;
            pathLeft.cubicTo(
                    ent_location + arcControlX, arcControlY,
                    ent_location + arcWidth - arcControlX, textHeight - arcControlY,
                    ent_location + arcWidth, textHeight);
            pathLeft.lineTo(mWidth, textHeight);
            pathLeft.lineTo(mWidth, mHeight);
            pathLeft.lineTo(0, mHeight);
            mPaint.setShadowLayer(1, 0, 2, Color.GRAY);
            canvas.drawPath(pathLeft, mPaint);
        }

        drawTextContent(canvas);
    }

    /**
     * TAB文本
     *
     * @param canvas
     */
    private void drawTextContent(Canvas canvas) {
        for (int i = 0; i < tabTextList.size(); i++) {
            String strTabText = tabTextList.get(i);
            Rect rectText = new Rect();
            textPaint.getTextBounds(strTabText, 0, strTabText.length(), rectText);
            int strWidth = rectText.width();
            int strHeight = rectText.height();


            if (i == tabPosition) {//选中的tab项文本
                textPaint.setColor(tabSelectTextColor);

            } else {
                textPaint.setColor(tabTextColor);
            }

            if (i == 0) {
                //第一个
                canvas.drawText(strTabText, (textWidth + arcWidth / 2) / 2 - strWidth / 2, textHeight / 2 + strHeight / 2, textPaint);

            } else if (i == tabTextList.size() - 1) {
                //最后一个
                canvas.drawText(strTabText, mWidth - (textWidth / 2 + strWidth / 2), textHeight / 2 + strHeight / 2, textPaint);

            } else {
                //中间
                canvas.drawText(strTabText, textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2, textHeight / 2 + strHeight / 2, textPaint);

            }
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = (int) textHeight;
        } else {
            mHeight = heightSpecSize;
        }

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = right - left;
        mHeight = bottom - top;
        centerX = mWidth / 2;
        textWidth = (mWidth - arcWidth * (tabNumber - 1)) / tabNumber;

        //设置ViewPager2位置
        mViewPager2.layout(0, (int) textHeight, mWidth, mHeight);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isHandleClick = false;//是否处理点击事件

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                for (int i = 0; i < tabNumber; i++) {
                    if (x <= ((i + 1) * textWidth + i * arcWidth + arcWidth / 2) && y < textHeight) {//点击的第一个按钮
                        tabPosition = i;
                        if (onTabClickListener != null) {
                            if (mViewPager2 != null)
                                mViewPager2.setCurrentItem(tabPosition);
                            onTabClickListener.tabSelectedListener(tabPosition);
                        }
                        invalidate();
                        isHandleClick = true;
                        break;
                    }
                }
                return isHandleClick;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 选中
     *
     * @param tabPosition
     */
    public void setSelectTab(int tabPosition) {
        if (tabPosition < tabNumber) {
            this.tabPosition = tabPosition;
            invalidate();
            if (onTabClickListener != null) {
                onTabClickListener.tabSelectedListener(tabPosition);
            }
        }
    }

    /**
     * 设置TAb数据
     *
     * @param tabTexts
     */
    public CardViewpager setTabTextList(List<String> tabTexts) {
        tabTextList = tabTexts;
        tabNumber = tabTextList.size();
        return this;
    }


    /**
     * tab选择监听
     *
     * @param onTabClickListener
     */
    public CardViewpager addTabSelectedListener(CardViewpagerTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
        return this;
    }

    /**
     * 设置ViewPager2
     *
     * @param mViewPager2
     * @return
     */
    private CardViewpager setViewPager2(ViewPager2 mViewPager2) {
        this.mViewPager2 = mViewPager2;
        //设置viewpager滑动监听
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabPosition = position;
                invalidate();
            }
        });
        return this;
    }

    /**
     * 设置ViewPager2适配器
     *
     * @param adapter
     */
    public CardViewpager setViewPagerAdapter(FragmentStateAdapter adapter) {
        mViewPager2.setAdapter(adapter);
        invalidate();
        return this;
    }

    public ViewPager2 getViewPager2() {
        return mViewPager2;
    }

    /**
     * 设置底层卡片 默认黑色
     *
     * @param color
     * @return
     */
    public CardViewpager setBottomCardColor(int color) {
        this.bottomCardColor = color;
        invalidate();
        return this;
    }

    /**
     * 设置顶层卡片 默认黑色
     *
     * @param color
     * @return
     */
    public CardViewpager setTopCardColor(int color) {
        this.topCardColor = color;
        invalidate();
        return this;
    }

    /**
     * 底层卡片降低高度
     *
     * @param height
     * @return
     */
    public CardViewpager setLowerHeight(int height) {
        this.lowerHeight = height;
        invalidate();
        return this;
    }
    /**
     * 未选中字体颜色
     * @param color
     * @return
     */
    public CardViewpager setTabTextColor(int color) {
        this.tabTextColor = color;//文本选中颜色
        invalidate();
        return this;
    }
    /**
     * 选中字体颜色
     * @param color
     * @return
     */
    public CardViewpager setTabSelectTextColor(int color) {
        this.tabSelectTextColor = color;//文本选中颜色
        invalidate();
        return this;
    }

    /**
     * 设置标签字体大小
     *
     * @param size
     * @return
     */
    public CardViewpager setTabTextSize(float size) {
        this.tabTextSize = JLDisplayUtil.spToPx(mContext, size);
        invalidate();
        return this;
    }

    /**
     * 设置曲线弧度 值越大，弧度越大
     * @param arcControlX
     * @return
     */
    public CardViewpager setArcControlX(int arcControlX){
        this.arcControlX = arcControlX;
        invalidate();
        return this;
    }
}
