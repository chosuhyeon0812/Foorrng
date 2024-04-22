import pandas as pd
from statsmodels.tsa.arima.model import ARIMA
from matplotlib import pyplot as plt

# Load your data
tmp_df = pd.read_csv(r'C:\Users\SSAFY\Desktop\bigdata\dir\2월.csv', encoding='cp949')

# Create a 'date' column by concatenating year with month and day columns
tmp_df['date'] = '2024-' + tmp_df['월'].astype(str).str.zfill(2) + '-' + tmp_df['일'].astype(str).str.zfill(2)
tmp_df['date'] = pd.to_datetime(tmp_df['date'])
# Filter data for the specific neighborhood '화원읍'

for region in tmp_df['동읍면']:
    try:
        neighborhood_data = tmp_df[(tmp_df['동읍면'] == region) & (tmp_df['승하차'] == '하차')]
        neighborhood_data.to_csv(rf'C:\Users\SSAFY\Desktop\bigdata\dir\townstation\{region}_승하차정보.csv', encoding='cp949', index=False)
    except:
        continue;
neighborhood_data.set_index('date', inplace=True)  # date 기준으로 sort
neighborhood_data.sort_index(inplace=True)

print(neighborhood_data.index.freq)

neighborhood_data = neighborhood_data.resample('D').sum()  # 시간을 기준으로 동들의 하차 값이 누적된다.

neighborhood_data['일계'].plot(title='Daily Population in Test')  # Adjust 'population' as necessary
plt.show()

model = ARIMA(neighborhood_data['일계'], order=(1, 1, 1))  # Adjust 'population' as necessary
model_fit = model.fit()

# print(model_fit.summary())


forecast = model_fit.forecast(steps=30)
for idx in range(len(forecast.values)):
    print(forecast.values[idx], end=" ")
print(f"Predicted population for the next day in 화원읍: {forecast.values[23]}")

print("END")



