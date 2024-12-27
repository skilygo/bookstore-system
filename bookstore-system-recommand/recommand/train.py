# train.py

import torch
import torch.optim as optim
from utils.data_loader import create_dataloader
from data.dataset import load_data
from model.recommendation_model import RecommendationModel

# 加载数据
df = load_data()

# 将数据转为 Tensor
user_ids = df['user_id'].values
book_ids = df['book_id'].values
view_time = df['view_time'].values

user_tensor = torch.tensor(user_ids, dtype=torch.long)
book_tensor = torch.tensor(book_ids, dtype=torch.long)
view_time_tensor = torch.tensor(view_time, dtype=torch.float32)

# 创建数据加载器
dataloader = create_dataloader(user_tensor, book_tensor, view_time_tensor)

# 获取用户数和书籍数
num_users = df['user_id'].nunique()
num_books = df['book_id'].nunique()

# 创建模型
embedding_dim = 50
model = RecommendationModel(num_users, num_books, embedding_dim)

# 设置损失函数和优化器
criterion = torch.nn.MSELoss()
optimizer = optim.Adam(model.parameters(), lr=0.001)

# 训练模型
epochs = 10
for epoch in range(epochs):
    model.train()
    total_loss = 0
    for batch in dataloader:
        user_ids = batch['user_id']
        book_ids = batch['book_id']
        view_time = batch['view_time']

        optimizer.zero_grad()
        print(user_ids[:10])  # 打印前 10 个用户 ID
        print(book_ids[:10])  # 打印前 10 个书籍 ID
        print(user_ids.max())  # 打印最大用户 ID
        print(book_ids.max())  # 打印最大书籍 ID
        user_ids = user_ids - 1  # 用户 ID 调整
        book_ids = book_ids - 1  # 书籍 ID 调整
        # 前向传播
        predictions = model(user_ids, book_ids, view_time)

        # 假设目标值是浏览时长
        targets = view_time  # 使用 view_time 作为目标

        loss = criterion(predictions.squeeze(), targets)
        loss.backward()
        optimizer.step()

        total_loss += loss.item()

    print(f"Epoch {epoch + 1}/{epochs}, Loss: {total_loss / len(dataloader)}")

# 保存模型
torch.save(model.state_dict(), 'recommendation_model.pth')
print("Model saved!")
