package com.jonhatfield.aidemo.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String imageFileName) {
        super(String.format("Image file '%s' not found. Check the src/main/resources folder", imageFileName));
    }
}