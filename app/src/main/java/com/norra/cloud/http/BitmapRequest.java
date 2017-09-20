package com.norra.cloud.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.norra.cloud.http.frame.HError;
import com.norra.cloud.http.frame.NetworkResponse;
import com.norra.cloud.http.frame.Request;
import com.norra.cloud.http.frame.Response;
import com.norra.cloud.utils.Logger;

/**
 * Created by liurenhui on 15/1/20.
 */
public class BitmapRequest extends Request<Bitmap> {

    public BitmapRequest(String imageUrl) {
        super(imageUrl);
    }

    @Override
    public Response<Bitmap> parseResponse(NetworkResponse response) {
        Bitmap bm = null;
        try {
            bm = BitmapFactory.decodeByteArray(response.data, 0, response.data.length);
            return Response.success(bm);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new HError(e, HError.PARSE_ERROR));
        }
    }
}
