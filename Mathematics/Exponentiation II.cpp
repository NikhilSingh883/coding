#include <bits/stdc++.h> 
using namespace std;
#define ll long long int
#define pb push_back
#define rsz resize
#define endl '\n'
#define all(x) begin(x), end(x)
#define sz(x) (ll)(x).size()
#define pi  pair<int,int>
#define f first
#define s second
#define pll pair<ll, ll>
#define v(x) vector<x>
#define mp make_pair
const ll mxN = 2e5;
const ll mod = 1e9 + 7;
const ll N = 1005;
#define rep(x,start,end) for(auto x=(start)-((start)>(end));x!=(end)-((start)>(end));((start)<(end)?x++:x--))

ll modExp(ll x,ll n,ll m){
    if(n==0) return 1;

    ll u = modExp(x,n/2,m);
    u = (u*u)%m;

    if(n%2) u = (u*x)%m;

    return u%m;
}

int main(){
 
    ll t;
    cin >> t;

    while(t--){
        ll a,b,c;
        cin >> a >> b >> c;

        c = c%(mod-2);

        b = modExp(b,c,mod-1);

        a = modExp(a,b,mod);

        cout << a << endl;
    }
}