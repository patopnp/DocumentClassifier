# Document classifier
This is a Java application that classifies text documents in one of the given categories. Training datasets for each categories must be supplied by the user.
For the given datasets it achieves an accuracy of over 90% over a sample of 100 documents when fitted into one of 3 categories, results may vary according to training data and samples.

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

The classifier is based on the Naive Bayes algorithm for classification which assumes probabilistic independence between words occurrences. Laplace smoothing (α=1) is applied.

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

Now by assuming conditional independence in words ocurrence ("naive"), that is that the appearence of a word is not probabilistically linked to the appearence of another, we may write each feature probability of occurence as ![image](https://github.com/user-attachments/assets/56e52d1a-fe22-4e33-8ce5-cb49374b6e6b)

Thus we have that 

![image](https://github.com/user-attachments/assets/bed63bb9-777a-46b0-bf50-fb668c97be73)

 From which the most likely class is selected:

![image](https://github.com/user-attachments/assets/761c430a-f9bc-498c-96ec-bec79270a74e)

Where <b>ŷ</b> is the class with the highest likelihood.



From here to estimate the conditional probability of words occurrence we use the bag-of-words model, counting words appearence on each class.

![image](https://github.com/user-attachments/assets/a5555a92-855c-40e2-8783-e16a828cdd47)

Where 

![image](https://github.com/user-attachments/assets/f3617e5e-87ea-441f-b7b1-49e7ea1a986e) is the number of occurrences of the word i in class <b><i>k</b></i>

![image](https://github.com/user-attachments/assets/91bc2afd-2e09-47dc-9a40-fd495e71147e) is the total number of words in class <b><i>k</b></i>

Laplace additive smoothing is applied to avoid nonocurrences nullifying the product. We can rewrite the expression as:

![image](https://github.com/user-attachments/assets/dc1c42ea-324a-4368-9e52-1cd20997492d) 

Where <b>T</b> is the size of the dictionary for all documents

Let ![image](https://github.com/user-attachments/assets/3cae87c1-43b5-45b3-a924-60768fdf2f24)

We then have:

![image](https://github.com/user-attachments/assets/12814d11-3d24-4668-8a8b-e370aad079d3)

And for the probability of a document belonging to a given class we have:

![image](https://github.com/user-attachments/assets/07e9fd44-fd31-424a-a9a2-22ad2ea875a4)

Where ![image](https://github.com/user-attachments/assets/dc48620b-3e02-422a-a152-11417c199b9a) is the number of documents of class <i><b>k</b></i>
and <i><b>D</b></i> is the total number of documents in the dataset


Putting all together we have:

![image](https://github.com/user-attachments/assets/0290c444-10fa-4d10-93da-90badb31b0b1)

To handle loss of precision for small values I worked with the terms' logarithms 

![image](https://github.com/user-attachments/assets/eb9c8989-c3b7-4916-91fb-936ab061cdc8)

from there argmax is selected.




















