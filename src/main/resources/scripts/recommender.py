import pymysql
import pandas as pd

# Connect to the database
connection = pymysql.connect(
    host='localhost:3307',
    user='root',
    password='rootroot',
    database='Local instance MySQL80'
)

# Fetch books and user book list
books_query = "SELECT * FROM books"
user_books_query = "SELECT b.* FROM books b JOIN booklist bl ON b.id = bl.book_id WHERE bl.user_id = %s"

books_df = pd.read_sql(books_query, connection)
user_books_df = pd.read_sql(user_books_query, connection, params=[user_id])

connection.close()

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import pandas as pd

def recommend_books(user_books_df, books_df, top_n=10):
    # Combine metadata for feature extraction
    books_df['features'] = books_df['title'] + " " + books_df['genre'] + " " + books_df['description']
    user_books_df['features'] = user_books_df['title'] + " " + user_books_df['genre'] + " " + user_books_df['description']

    # Vectorize the features using TF-IDF
    tfidf = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf.fit_transform(books_df['features'])
    user_matrix = tfidf.transform(user_books_df['features'])

    # Calculate cosine similarity between user books and all books
    similarity_scores = cosine_similarity(user_matrix, tfidf_matrix)

    # Average similarity scores across all user's books
    average_similarity = similarity_scores.mean(axis=0)

    # Add scores to the books dataframe
    books_df['similarity'] = average_similarity

    # Exclude books the user already has
    recommended_books = books_df[~books_df['id'].isin(user_books_df['id'])]

    # Sort by similarity score and return top N recommendations
    return recommended_books.sort_values(by='similarity', ascending=False).head(top_n)
