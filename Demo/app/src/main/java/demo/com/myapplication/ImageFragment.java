package demo.com.myapplication;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import demo.com.myapplication.view.OnPhotoTapListener;
import demo.com.myapplication.view.PhotoDraweeView;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ImageFragment extends Fragment {

    private static final String IMAGE_URL = "image";

    private PhotoDraweeView photoDraweeView;

    private String imageUrl;

    public static ImageFragment newInstance(String param1) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            imageUrl = getArguments().getString(IMAGE_URL);
        }
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        photoDraweeView = (PhotoDraweeView) view.findViewById(R.id.photoView);
        initData(imageUrl);
        return view;
    }

    private void initData(String url) {
        if (!TextUtils.isEmpty(url)) {
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(url);//设置图片url
            controller.setOldController(photoDraweeView.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null || photoDraweeView == null) {
                        return;
                    }
                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            photoDraweeView.setController(controller.build());
        } else {
            Toast.makeText(getActivity(), "图片获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initEvent() {
        //添加点击事件
        photoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                getActivity().finish();
            }
        });
    }
}
