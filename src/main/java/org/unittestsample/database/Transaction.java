package org.unittestsample.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anyonefrom on 1/29/15.
 */
//TODO: refactor to create hierarchical structure of transaction (verisure, docusure, instasure, offline)
public class Transaction {
    private String transactionId;
    private TransactionType transactionType;
    private String consumerReference;
    private String transactionReference;
    private boolean bothSides;
    private boolean secondaryDocument;
    private String frontPicturePath;
    private String backPicturePath;
    private String secondaryPicturePath;
    private List<String> facesPath = new ArrayList<String>();
    private String login;
    private String password;
    private String token;
    private EchipData echipData;

    private boolean finished;
    private boolean offline; //TODO remove later

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getConsumerReference() {
        return consumerReference;
    }

    public void setConsumerReference(String consumerReference) {
        this.consumerReference = consumerReference;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public boolean isBothSides() {
        return bothSides;
    }

    public void setBothSides(boolean bothSides) {
        this.bothSides = bothSides;
    }

    public boolean isSecondaryDocument() {
        return secondaryDocument;
    }

    public void setSecondaryDocument(boolean secondaryDocument) {
        this.secondaryDocument = secondaryDocument;
    }

    public String getFrontPicturePath() {
        return frontPicturePath;
    }

    public void setFrontPicturePath(String frontPicturePath) {
        this.frontPicturePath = frontPicturePath;
    }

    public String getBackPicturePath() {
        return backPicturePath;
    }

    public void setBackPicturePath(String backPicturePath) {
        this.backPicturePath = backPicturePath;
    }

    public String getSecondaryPicturePath() {
        return secondaryPicturePath;
    }

    public void setSecondaryPicturePath(String secondaryPicturePath) {
        this.secondaryPicturePath = secondaryPicturePath;
    }

    public List<String> getFacesPath() {
        return facesPath;
    }

    public void setFacesPath(List<String> facesPath) {
        this.facesPath = facesPath;
    }

    public void addFace(String path) {
        facesPath.add(path);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public EchipData getEchipData() {
        return echipData;
    }

    public void setEchipData(EchipData echipData) {
        this.echipData = echipData;
    }

    public enum TransactionType {
        type1,
        type2;
    }

}