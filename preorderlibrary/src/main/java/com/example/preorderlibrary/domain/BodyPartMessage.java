package com.example.preorderlibrary.domain;

/**
 * Created by willyou on 2016/11/14.
 */

public  class BodyPartMessage{
        private String secondID;
        private String secondTitle;
        private String bodypartImage;
        private String bodypartImageID;
        public BodyPartMessage (){

        }
        public BodyPartMessage(String secondID,String secondTitle,String bodypartImage,String bodypartImageID){
            this.secondID=secondID;
            this.secondTitle=secondTitle;
            this.bodypartImage=bodypartImage;
            this.bodypartImageID=bodypartImageID;
        }

        public String getSecondID() {
            return secondID;
        }

        public String getSecondTitle() {
            return secondTitle;
        }

        public void setSecondTitle(String secondTitle) {
            this.secondTitle = secondTitle;
        }

        public String getBodypartImage() {
            return bodypartImage;
        }

        public void setBodypartImage(String bodypartImage) {
            this.bodypartImage = bodypartImage;
        }

        public String getBodypartImageID() {
            return bodypartImageID;
        }

        public void setBodypartImageID(String bodypartImageID) {
            this.bodypartImageID = bodypartImageID;
        }

        public void setSecondID(String secondID) {
            this.secondID = secondID;

        }

    @Override
    public String toString() {
        return "BodyPartMessage{" +
                "secondID='" + secondID + '\'' +
                ", secondTitle='" + secondTitle + '\'' +
                ", bodypartImage='" + bodypartImage + '\'' +
                ", bodypartImageID='" + bodypartImageID + '\'' +
                '}';
    }
}

