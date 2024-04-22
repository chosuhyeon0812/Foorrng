# Step 1: KNN Results Integration
# KNN probabilities for each dataset for food items

knn_results = {
    "Rice Bowl":[0.12],
    "Famine Crops":[0.11],
    'Electric Grilled Whole Chicken':[0.01],
    'Skewer':[0.01],
    'Takoyaki':[0.01],
    'Taco & Kebab':[0.01],
    'Korean Street Food':[0.01],
    "Bread": [0.36, 0.17, 0.17, 0.17],
    "Cafe & Dessert": [0.12],
    'Etc':[0.01]
}

# Calculate combined score for each food item based on KNN results
combined_scores = {item: sum(probabilities) / len(probabilities) for item, probabilities in knn_results.items()}

# Display the combined KNN scores
print("Combined KNN Scores for Each Food Item:")
for item, score in combined_scores.items():
    print(f"{item}: {score:.2f}")

# Step 2: 시계열 분석에 대한 각각의 동네 데이터
# 배열에 동네들 다 담고
# Simulated overall score percentage for the neighborhood for the next day (between 0 and 1)
neighborhood_score = 0.85  # Example value representing the overall condition of the neighborhood

# Display the neighborhood score
print(f"\nOverall Neighborhood Score: {neighborhood_score:.2f}")


# Step 3: Ensemble Model - Adjust KNN Scores Based on Neighborhood Score
final_scores = {item: score * neighborhood_score for item, score in combined_scores.items()} # 앙상블을 통한 값을 가져온다.

print("\n 동네에 대한 최종 점수를 리턴한다.")
# 아이템의 점수에 따라 sort를 시킨 최종 값을 저장한다.
sorted_final_scores = sorted(final_scores.items(), key=lambda item: item[1], reverse=True)
# 리턴~! 끄으으으으읕~!
for item, score in sorted_final_scores:
    print(f"{item}: {score:.2f}")






