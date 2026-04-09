import axiosInstance from './axiosInstance';

export const bannerApi = {
  create: (data) =>
    axiosInstance.post('/banners', data),

  getMyBanners: () =>
    axiosInstance.get('/banners'),

  getById: (id) =>
    axiosInstance.get(`/banners/${id}`),

  toggleActive: (id) =>
    axiosInstance.patch(`/banners/${id}/toggle`),

  delete: (id) =>
    axiosInstance.delete(`/banners/${id}`),

  recordView: (bannerId, placementId) =>
    axiosInstance.post(`/banners/${bannerId}/view`, null, {
      params: { placementId }
    }),

  recordClick: (bannerId, placementId) =>
    axiosInstance.post(`/banners/${bannerId}/click`, null, {
      params: { placementId }
    }),
};
