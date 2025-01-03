import json
import mysql.connector
import sys
import pandas as pd

# Function to process recommendations based on the user's book list
def get_book_recommendations(user_id):
    try:
        print(f"Starting recommendation process for user_id: {user_id}")

        # Connect to the database
        print("Connecting to the database...")
        connection = mysql.connector.connect(
            host="localhost",
            user="root",
            password="rootroot",
            database="bookly"
        )
        cursor = connection.cursor(dictionary=True)
        print("Connected to the database successfully.")

        # Fetch the user's book list based on user_id
        print(f"Fetching books for user_id = {user_id}...")
        cursor.execute(f"SELECT * FROM book WHERE user_id = {user_id}")
        user_books = cursor.fetchall()
        print(f"Fetched {len(user_books)} books for user_id = {user_id}.")

        # Fetch all books for comparison
        print("Fetching all books for comparison...")
        cursor.execute("SELECT * FROM book")
        all_books = cursor.fetchall()
        print(f"Fetched {len(all_books)} books from the database.")

        cursor.close()
        connection.close()

        # Convert books data to DataFrame
        print("Converting user and all books data to DataFrame...")
        df_user_books = pd.DataFrame(user_books)
        df_all_books = pd.DataFrame(all_books)
        print(f"User books DataFrame shape: {df_user_books.shape}, All books DataFrame shape: {df_all_books.shape}")

        # Get the genres and authors for the user's books
        print("Extracting genres and authors from user's books...")
        user_genres = df_user_books['genre'].tolist()
        user_authors = df_user_books['author'].tolist()
        print(f"User's genres: {user_genres}")
        print(f"User's authors: {user_authors}")

        # Filter books by matching genres or authors
        print("Filtering books by matching genres or authors...")
        recommended_books = df_all_books[
            df_all_books['genre'].isin(user_genres) | df_all_books['author'].isin(user_authors)
        ]
        print(f"Number of books after filtering: {recommended_books.shape[0]}")

        # Sort by total_page and return the top 10 recommendations
        print("Sorting the filtered books by total pages...")
        recommended_books = recommended_books.sort_values(by='total_page', ascending=True).head(10)
        print(f"Top 10 recommendations selected.")

        # Return as a list of dictionaries
        return recommended_books.to_dict(orient='records')

    except mysql.connector.Error as err:
        print(f"Error: {err}")
        return []


# Main function to simulate receiving data from Spring Boot
if __name__ == "__main__":
    # Simulating user_id as 3 for testing
    user_id = 3

    print("Simulating recommendation process...")

    # Get recommendations based on the user's books
    recommendations = get_book_recommendations(user_id)

    # Output the recommendations as a JSON string
    print("Recommendations:")
    print(json.dumps(recommendations, indent=4))
