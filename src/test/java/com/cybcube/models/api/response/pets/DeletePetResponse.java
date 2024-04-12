package com.cybcube.models.api.response.pets;

public class DeletePetResponse{
    private final int code;
    private final String message;
    private final String type;

    public DeletePetResponse(){
        this.code = 0;
        this.message = "";
        this.type = "";
    }


    public DeletePetResponse(int responseCode, String message, String type) {
        this.code = responseCode;
        this.message = message;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
