import axiosInstance from './axiosInstance';

export const getMyCampaigns = () => axiosInstance.get('/campaigns');
export const createCampaign = (data) => axiosInstance.post('/campaigns', data);
export const updateStatus = (id, status) => axiosInstance.patch(`/campaigns/${id}/status?status=${status}`);
