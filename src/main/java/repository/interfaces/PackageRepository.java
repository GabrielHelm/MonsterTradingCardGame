package repository.interfaces;

import game.card.CardCollection;

import java.util.List;

public interface PackageRepository {
    void createPackage(CardCollection cardCollection);
    List<String> getCardIdsFromPackage(String packageId);
    String getRandomPackageId();
    void deletePackage(String packageId);

}
