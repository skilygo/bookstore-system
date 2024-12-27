# utils/data_loader.py

from torch.utils.data import Dataset, DataLoader


class UserBookInteractionDataset(Dataset):
    def __init__(self, user_tensor, book_tensor, view_time_tensor):
        self.user_tensor = user_tensor
        self.book_tensor = book_tensor
        self.view_time_tensor = view_time_tensor

    def __len__(self):
        return len(self.user_tensor)

    def __getitem__(self, idx):
        return {
            'user_id': self.user_tensor[idx],
            'book_id': self.book_tensor[idx],
            'view_time': self.view_time_tensor[idx]
        }


def create_dataloader(user_tensor, book_tensor, view_time_tensor, batch_size=32):
    dataset = UserBookInteractionDataset(user_tensor, book_tensor, view_time_tensor)
    return DataLoader(dataset, batch_size=batch_size, shuffle=True)
