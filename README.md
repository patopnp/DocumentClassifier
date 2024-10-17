# Document classifier
This is a Java application that classifies text documents in one of the given categories. Training datasets for each categories must be supplied by the user.
For the given datasets it achieves an accuracy of over 90% over a sample of 100 documents when fitted into one of 3 categories, result may vary according to training data an samples.

## Implementation details:

GUI is implemented through Java's Swing toolkit as already integrated into standard JDK.

### Software architecture:

#### MVC pattern.
Model: Refers to the data model classes (Categories, NaiveBayesClassifier) used for business logic, in this case probabilistic calculations.
View: Refers to the presentation of the data models, and GUI elements with which the users interacts (Swing Widgets).
Controller: Handles user inputs and updates the models. Provides the action listeners for the view components (Buttons, File choosers).

#### Observer pattern
For updating view components when the category files or path is changed.
Observers are the Category tab panels from the tab panel which needs to update directory field and files list. The subject is the category which is the model object that underlies all operations: classification calculations, category files, path. Whenever a change is effected to the subject all observers are notified to reflect changes from the model.

### Data structures:

Hash map for word counting used for bag-of-words model implementation, every word excluding noise-words is hashed. This allows for quick look-up and counter increments.

### Algorithms:

The classifier is based on the Naive Bayes algorithm for classification which assumes probabilistic independence between words occurrences Laplace smoothing (alpha=1) is applied.

Implementation is done at a fairly low-level, since it was implemented from scratch and not with any of the existing libraries. This was done deliberately as I was trying to get a grasp of the foundations of Machine Learning algorithms back in 2017.

#### Brief explanation:

The probability that a document characterized by a given set of features belongs to a certain class is given by:

![image](https://github.com/user-attachments/assets/baa4a501-f77e-4368-8bd6-f12665c369fc)

Where ![image](https://github.com/user-attachments/assets/8233d81f-e7c3-4712-bd86-fe323851592e) is the encoding of n features, in our case words occurences.

By use of Naive Bayes theorem the probability can be rewritten as:

![image](https://github.com/user-attachments/assets/9be4cbad-ba55-47de-a2c6-bf4d5d99b40b)

If we take only the numerator, given that the features remain constant, we have

![image](https://github.com/user-attachments/assets/a1bb468e-02b2-4942-a288-53846548783b)

Which is the joint probability, that is the probability that the features appeared together in a given document, among all features in all documents (not to be confused with the conditional probability, the probability that for a given document a set of words appears, as in the numerator's second factor)

Now by assuming conditional independence in words ("naive"), that is that the appearence of a word is not probabilistically linked to the appearence of another, we may write each feature probability of occurence as ![image](https://github.com/user-attachments/assets/56e52d1a-fe22-4e33-8ce5-cb49374b6e6b)

Thus we have that 

![image](https://github.com/user-attachments/assets/bed63bb9-777a-46b0-bf50-fb668c97be73)

 From which the most likely class is selected:

![image](https://github.com/user-attachments/assets/761c430a-f9bc-498c-96ec-bec79270a74e)

Where ŷ is the class with the highest likelihood.

To handle loss of precision for small values I worked with the terms' logarithms 

From here to estimate the conditional probability of words occurrence we use the bag-of-words model, counting words appearence on each class.










