package uk.co.vism.wordbox.managers;

import android.content.Context;

import io.realm.Realm;
import uk.co.vism.wordbox.models.User;
import uk.co.vism.wordbox.network.RestClient;
import uk.co.vism.wordbox.network.RestClient_;

/**
 * Created by Ted Eriksson on 30/03/15.
 */
public class RestClientManager {
    RestClient restClient;

    private static RestClientManager instance = null;

    protected RestClientManager(Context context) {
        restClient = new RestClient_(context);
    }

    public static RestClientManager getInstance(Context context) {
        if (instance == null) {
            instance = new RestClientManager(context);
        }
        return instance;
    }

    public static void updateUser(Context context, int id) {
        RestClientManager instance = getInstance(context);

        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        String json = instance.restClient.getUser(id).toString();
        realm.createOrUpdateObjectFromJson(User.class, json);

        realm.commitTransaction();

        realm.close();
    }
}