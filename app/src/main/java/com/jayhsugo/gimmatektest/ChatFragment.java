package com.jayhsugo.gimmatektest;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private String postAPI = "https://hahago-api-tesla.appspot.com/fortest/api/";
    private List<Chat> chatList;

    public ChatFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        setHasOptionsMenu(true);
        requestQueue = Volley.newRequestQueue(getActivity());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 使用Volley框架發出Http POST請求得到回傳資料
        getJsonDataFromServer();
    }

    private void getJsonDataFromServer() {

        // 將User座標資料包裝成JSON物件
        JSONObject userMapData = new JSONObject();
        try {
            userMapData.put("userlat", 24.7844431);
            userMapData.put("userlng", 121.0172038);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postAPI, userMapData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        analyzeResponseJsonData(response);
                        Log.d("MyLog", "response:" + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MyLog", "VolleyError:" + error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void analyzeResponseJsonData(JSONObject response) {

        // 將回傳資料擷取成完整的Json格式
        String responseString = response.toString();
        int endIndex = responseString.indexOf("}");
        Log.d("MyLog", "endIndex:" + endIndex);
        String newJsonString = responseString.substring( 0, endIndex) + "}]}";
        Log.d("MyLog", "newJsonString:" + newJsonString);

        JSONObject j;
        chatList = new ArrayList<>();

        try {
            j = new JSONObject(newJsonString);
            for (int i = 0; i < j.length(); i++) {
                String userName = j.getJSONArray("feeds").getJSONObject(i).getString("userName");
                String uid = j.getJSONArray("feeds").getJSONObject(i).getString("uid");
                String timeDiff = j.getJSONArray("feeds").getJSONObject(i).getString("timeDiff");
                String distance = j.getJSONArray("feeds").getJSONObject(i).getString("distance");
                String body = j.getJSONArray("feeds").getJSONObject(i).getString("body");
                String photo = j.getJSONArray("feeds").getJSONObject(i).getString("photo");
                String userPhoto = j.getJSONArray("feeds").getJSONObject(i).getString("userPhoto");
                String tag = j.getJSONArray("feeds").getJSONObject(i).getString("tag");
                int numGood = j.getJSONArray("feeds").getJSONObject(i).getInt("numGood");
                int numBad = j.getJSONArray("feeds").getJSONObject(i).getInt("numBad");
                chatList.add(new Chat(userName, uid, timeDiff, distance, body, photo, userPhoto, tag, numGood, numBad));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("MyLog", "JSONException:" + e.toString());
        }
        createRecyclerView();
    }

    private void createRecyclerView() {
        Log.d("MyLog", "createRecyclerView()");
        ChatAdapter chatAdapter = new ChatAdapter(getActivity().getLayoutInflater());
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
    }

    private class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
        private LayoutInflater inflater;

        public ChatAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imgHead, imgEllipsis, imgMood, imgPhoto, imgLike, imgHate;
            private TextView tvName, tvUid, tvTimeDiff, tvDistance, tvPosting, tvNumGood, tvNumBad;

            public ViewHolder(View itemView) {
                super(itemView);
                imgHead = itemView.findViewById(R.id.imgHead);
                imgEllipsis = itemView.findViewById(R.id.imgEllipsis);
                imgMood = itemView.findViewById(R.id.imgMood);
                imgPhoto = itemView.findViewById(R.id.imgPhoto);
                imgLike = itemView.findViewById(R.id.imgLike);
                imgHate = itemView.findViewById(R.id.imgHate);
                tvName = itemView.findViewById(R.id.tvName);
                tvUid = itemView.findViewById(R.id.tvUid);
                tvTimeDiff = itemView.findViewById(R.id.tvTimeDiff);
                tvDistance = itemView.findViewById(R.id.tvDistance);
                tvPosting = itemView.findViewById(R.id.tvPosting);
                tvNumGood = itemView.findViewById(R.id.tvNumGood);
                tvNumBad = itemView.findViewById(R.id.tvNumBad);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.card_chat, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int position) {
            final Chat chat = chatList.get(position);
            Picasso.with(getContext()).load(chat.getUserPhoto())
                    .transform(new CircleTransform()) // 設置圓形圖片
                    .into(viewHolder.imgHead);
            viewHolder.imgEllipsis.setImageResource(R.drawable.ic_ellipsis);
            viewHolder.imgLike.setImageResource(R.drawable.ic_like_normal);
            viewHolder.imgHate.setImageResource(R.drawable.ic_hate_normal);
            viewHolder.tvName.setText(chat.getUserName());
            viewHolder.tvUid.setText(chat.getUid());
            viewHolder.tvTimeDiff.setText(chat.getTimeDiff());
            viewHolder.tvDistance.setText(chat.getDistance());
            viewHolder.tvNumGood.setText(String.valueOf(chat.getNumGood()));
            viewHolder.tvNumBad.setText(String.valueOf(chat.getNumBad()));
            viewHolder.tvPosting.setText(chat.getBody());

            if (chat.getTag().equals("good")) {
                viewHolder.imgMood.setImageResource(R.drawable.ic_good_mood);
            } else {
                viewHolder.imgMood.setImageResource(R.drawable.ic_bad_mood);
            }

            Transformation transformation = new Transformation() {

                @Override
                public Bitmap transform(Bitmap source) {
                    int targetWidth = viewHolder.imgPhoto.getWidth();
                    Log.d("MyLog", "targetWidth:" + targetWidth);

                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                    int targetHeight = (int) (targetWidth * aspectRatio);
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                }

                @Override
                public String key() {
                    return "transformation" + " desiredWidth";
                }
            };

            Picasso.with(getContext()).load(chat.getPhoto())
                    .transform(transformation)
                    .transform(new RoundTransform(getContext()))
                    .into(viewHolder.imgPhoto);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        @Override
        public int getItemCount() {
            return chatList.size();
        }
    }
}
