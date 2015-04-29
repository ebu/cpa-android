package ch.ebu.cpa.webservice;

import ch.ebu.cpa.model.CpaClientIdentity;
import ch.ebu.cpa.model.CpaClientInfo;
import ch.ebu.cpa.model.CpaClientToken;
import ch.ebu.cpa.model.CpaUserCode;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 *  Copyright (c) European Broadcasting Union. All rights reserved.
 *
 *  Licence information is available from the LICENCE file.
 */
public interface CpaService {

    @POST("/register")
    void clientRegistration(@Body CpaClientInfo clientInfo, Callback<CpaClientIdentity> callback);

    @POST("/token")
    void clientToken(@Body CpaClientIdentity clientIdentity, Callback<CpaClientToken> callback);

    @POST("/associate")
    void userCodeRequest(@Body CpaClientIdentity identity, Callback<CpaUserCode> callback);

}
