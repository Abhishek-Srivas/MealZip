package users.com.mealzip.Helper;

import android.app.ProgressDialog;
import android.content.Context;

public class helper {
    Context context;

    public class PbFn {
        ProgressDialog pd = new ProgressDialog(context);

        public void showPb() {
            pd.setMax(100);
            pd.setMessage("Loading...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
            pd.setCancelable(false);
        }

        public void hidePb() {
            pd.dismiss();
        }
    }

    public helper(Context context) {
        this.context = context;
    }

}
