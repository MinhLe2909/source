package tamhoang.ldpro4.akaman;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;


public class InputStreamVolleyRequest extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    private final Map<String, String> mParams;
    public Map<String, String> responseHeaders;

    public InputStreamVolleyRequest(int method, String mUrl, Response.Listener<byte[]> listener, Response.ErrorListener errorListener, HashMap<String, String> params) {
        super(method, mUrl, errorListener);
        setShouldCache(false);
        this.mListener = listener;
        this.mParams = params;
    }

    @Override // com.android.volley.Request
    public void deliverResponse(byte[] response) {
        this.mListener.onResponse(response);
    }

    @Override // com.android.volley.Request
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.mParams;
    }

    @Override // com.android.volley.Request
    public Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        this.responseHeaders = response.headers;
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}
