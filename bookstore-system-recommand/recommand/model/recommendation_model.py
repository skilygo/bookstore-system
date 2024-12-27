# models/recommendation_model.py

import torch
import torch.nn as nn


class RecommendationModel(nn.Module):
    def __init__(self, num_users, num_books, embedding_dim):
        super(RecommendationModel, self).__init__()
        self.user_embedding = nn.Embedding(num_users, embedding_dim)
        self.book_embedding = nn.Embedding(num_books, embedding_dim)
        self.fc1 = nn.Linear(embedding_dim * 2 + 1, 128)  # 2嵌入向量 + 1交互特征
        self.fc2 = nn.Linear(128, 1)

    def forward(self, user_id, book_id, view_time):
        user_emb = self.user_embedding(user_id)
        book_emb = self.book_embedding(book_id)
        x = torch.cat([user_emb, book_emb, view_time.unsqueeze(1)], dim=-1)  # 拼接
        x = torch.relu(self.fc1(x))
        x = self.fc2(x)
        return x
