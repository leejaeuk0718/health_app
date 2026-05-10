package healthApp_backend.security.ouath;

public interface OAuth2UserInfo {

    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();
}
