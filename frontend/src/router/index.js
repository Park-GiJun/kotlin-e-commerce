import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Dashboard from '../views/Dashboard.vue'
import Products from '../views/Products.vue'
import ProductDetail from '../views/ProductDetail.vue'
import Cart from '../views/Cart.vue'
import Checkout from '../views/Checkout.vue'
import OrderHistory from '../views/OrderHistory.vue'
import OrderDetail from '../views/OrderDetail.vue'
import Profile from '../views/Profile.vue'
import Wishlist from '../views/Wishlist.vue'
import CustomerService from '../views/CustomerService.vue'
import Notice from '../views/Notice.vue'
import Inquiry from '../views/Inquiry.vue'
import FAQ from '../views/FAQ.vue'
import Coupons from '../views/Coupons.vue'
import Points from '../views/Points.vue'
import AdminUsers from '../views/AdminUsers.vue'
import AdminProducts from '../views/AdminProducts.vue'
import AdminCategories from '../views/AdminCategories.vue'
import AdminDashboard from '../views/AdminDashboard.vue'
import AdminOrders from '../views/AdminOrders.vue'
import AdminReviews from '../views/AdminReviews.vue'
import AdminInquiries from '../views/AdminInquiries.vue'

const routes = [
  {
    path: '/',
    redirect: '/dashboard'
  },
  // 비로그인 접근 가능 (공개 페이지)
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { guest: true } // 로그인 시 대시보드로 리다이렉트
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: { guest: true }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
    meta: { public: true } // 비로그인도 접근 가능
  },
  {
    path: '/products',
    name: 'Products',
    component: Products,
    meta: { public: true }
  },
  {
    path: '/products/:id',
    name: 'ProductDetail',
    component: ProductDetail,
    meta: { public: true }
  },
  // 로그인 필요 (회원 전용)
  {
    path: '/cart',
    name: 'Cart',
    component: Cart,
    meta: { requiresAuth: true }
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: Checkout,
    meta: { requiresAuth: true }
  },
  {
    path: '/orders',
    name: 'OrderHistory',
    component: OrderHistory,
    meta: { requiresAuth: true }
  },
  {
    path: '/orders/:id',
    name: 'OrderDetail',
    component: OrderDetail,
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile,
    meta: { requiresAuth: true }
  },
  {
    path: '/wishlist',
    name: 'Wishlist',
    component: Wishlist,
    meta: { requiresAuth: true }
  },
  {
    path: '/coupons',
    name: 'Coupons',
    component: Coupons,
    meta: { requiresAuth: true }
  },
  {
    path: '/points',
    name: 'Points',
    component: Points,
    meta: { requiresAuth: true }
  },
  // 고객센터 (공개)
  {
    path: '/customer-service',
    name: 'CustomerService',
    component: CustomerService,
    meta: { public: true }
  },
  {
    path: '/notice',
    name: 'Notice',
    component: Notice,
    meta: { public: true }
  },
  {
    path: '/faq',
    name: 'FAQ',
    component: FAQ,
    meta: { public: true }
  },
  {
    path: '/inquiry',
    name: 'Inquiry',
    component: Inquiry,
    meta: { requiresAuth: true }
  },
  // 관리자 전용
  {
    path: '/admin',
    redirect: '/admin/dashboard'
  },
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    component: AdminDashboard,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/orders',
    name: 'AdminOrders',
    component: AdminOrders,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/users',
    name: 'AdminUsers',
    component: AdminUsers,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/products',
    name: 'AdminProducts',
    component: AdminProducts,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/categories',
    name: 'AdminCategories',
    component: AdminCategories,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/reviews',
    name: 'AdminReviews',
    component: AdminReviews,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/inquiries',
    name: 'AdminInquiries',
    component: AdminInquiries,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  // 기존 가격 관리 페이지는 상품 관리로 리다이렉트
  {
    path: '/admin/product-prices',
    redirect: '/admin/products'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    // 항상 맨 위로 스크롤
    return { top: 0, behavior: 'smooth' }
  }
})

// Navigation guard
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()

  // 관리자 페이지 - 관리자 권한 필요
  if (to.meta.requiresAdmin && !authStore.isAdmin) {
    next('/dashboard')
    return
  }

  // 로그인 필요 페이지 - 비로그인 시 로그인으로 이동
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login')
    return
  }

  // 게스트 전용 페이지 (로그인/회원가입) - 로그인 시 대시보드로 이동
  if (to.meta.guest && authStore.isAuthenticated) {
    next('/dashboard')
    return
  }

  next()
})

export default router
