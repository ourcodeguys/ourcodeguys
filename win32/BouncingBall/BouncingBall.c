#include <windows.h>
//#include <stdio.h>
//#include <stdlib.h>

#define TIMER_SEC 1
#define TIMER_MIN 2
#define CIRCLE_RADIUS 20
#define CHAM_DINH 1
#define CHAM_DAY 2
#define CHAM_TRAI 3
#define CHAM_PHAI 4

LRESULT CALLBACK WndProc (HWND, UINT, WPARAM, LPARAM);
/*Vẽ hình tròn theo tọa độ của tâm và bán kính*/
int drawCircle(HDC hdc, HBRUSH hBrush, HGDIOBJ hPen, int x, int y, int r);
/*Tính toán quỹ đạo*/
int computeOrbit(int x, int y, int radius, LPRECT lpRect);
void NewRect(LPRECT lpRect);

char szBuffer[256];
//unsigned int xCircle = CIRCLE_RADIUS+40, yCircle = CIRCLE_RADIUS+50,
//	xPrevCircle = CIRCLE_RADIUS, yPrevCircle = CIRCLE_RADIUS,
unsigned int xCircle = CIRCLE_RADIUS+100, yCircle = CIRCLE_RADIUS+100,
	xPrevCircle = CIRCLE_RADIUS, yPrevCircle = CIRCLE_RADIUS,
	// TODO:
	dx = 5, dy = 5; /*Độ tăng có giá trị tuyệt đối là 5*/
//bool b = FALSE;
HDC hdc;
PAINTSTRUCT Ps;
RECT rect, rect2;

int WINAPI WinMain (HINSTANCE hInstance, HINSTANCE hPrevInstance,
	LPSTR lpszCmdLine, int nCmdShow)
{
	static TCHAR szAppName[] = TEXT ("Bouncing Ball");
	HWND         hwnd;
	MSG          msg;
	WNDCLASS     wndclass;

	wndclass.style         = CS_HREDRAW | CS_VREDRAW;
	wndclass.lpfnWndProc   = WndProc;
	wndclass.cbClsExtra    = 0;
	wndclass.cbWndExtra    = 0;
	wndclass.hInstance     = hInstance;
	wndclass.hIcon         = LoadIcon (NULL, IDI_APPLICATION);
	wndclass.hCursor       = LoadCursor (NULL, IDC_ARROW);
	wndclass.hbrBackground = (HBRUSH) GetStockObject (WHITE_BRUSH);
	wndclass.lpszMenuName  = NULL;
	wndclass.lpszClassName = szAppName;

	if (!RegisterClass (&wndclass))
	{
		MessageBox (NULL, TEXT ("This program requires Windows NT!"),
			szAppName, MB_ICONERROR);
		return 0;
	}

	hwnd = CreateWindow (szAppName,
		TEXT ("Bouncing Ball (c) 2013 khoahv@hotmail.com"),
		WS_OVERLAPPEDWINDOW,
		CW_USEDEFAULT, CW_USEDEFAULT,
		CW_USEDEFAULT, CW_USEDEFAULT,
		NULL, NULL, hInstance, NULL);

	ShowWindow (hwnd, nCmdShow);
	UpdateWindow (hwnd);

	while (GetMessage (&msg, NULL, 0, 0))
	{
		TranslateMessage (&msg);
		DispatchMessage (&msg);
	}
	return msg.wParam;
}

LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{

	switch (msg)
	{
	case WM_CREATE:
		SetTimer(hwnd, TIMER_SEC, 10, NULL);
		GetClientRect(hwnd, &rect);
		return 0;
	case WM_PAINT:
		GetClientRect(hwnd, &rect);
		NewRect(&rect);
		hdc = BeginPaint(hwnd, &Ps);

		/*Vẽ Red Ball mới*/
		drawCircle(hdc, CreateSolidBrush(RGB(250, 25, 5)),
			GetStockObject(BLACK_PEN), xCircle, yCircle, CIRCLE_RADIUS);
		/*So sánh với left, right, top, bottom để xác định phương trình tham số*/
		switch (computeOrbit(xPrevCircle+dx, yPrevCircle+dy, CIRCLE_RADIUS, &rect))
		{
			/*Độ tăng tung độ đổi dấu khi chạm đỉnh hoặc đáy*/
		case CHAM_DINH:
		case CHAM_DAY:
			//MessageBoxA(NULL,"","",MB_OK);
			if (yPrevCircle < rect.bottom && yPrevCircle > rect.top)
			{
				dy = -dy;
			}
			break;
			/*Độ tăng hoành độ đổi dấu khi chạm cạnh trái hoặc phải*/
		case CHAM_TRAI:
		case CHAM_PHAI:
			if (xPrevCircle < rect.right && xPrevCircle > rect.left)
			{
				dx = -dx;
			}
			break;
		default:
			break;
		}
		xPrevCircle = xCircle; yPrevCircle = yCircle;
		xCircle += dx, yCircle += dy;

		EndPaint(hwnd, &Ps);
		return 0;
	case WM_DESTROY:
		KillTimer(hwnd, TIMER_SEC);
		PostQuitMessage(WM_QUIT);
		return 0;;
	case WM_TIMER:
		switch(wParam)
		{
		case TIMER_SEC:
			MessageBeep(-1);
			InvalidateRgn(hwnd, NULL, TRUE);
			GetClientRect(hwnd, &rect);
			break;
		case TIMER_MIN:
			break;
		}
		return 0;
	default:
		return DefWindowProc(hwnd, msg, wParam, lParam);
	}
	return 0;
}

int drawCircle(HDC hdc, HBRUSH hBrush, HGDIOBJ hPen, int x, int y, int r)
{
	/*if (x < r || y < r)
	return -1;*/
	SelectObject(hdc, hBrush);
	SelectObject(hdc, hPen);
	Ellipse(hdc, x - r , y - r, r + x, r + y);
	DeleteObject(hPen);
	DeleteObject(hBrush);
	return 0;
}

int computeOrbit(int x, int y, int radius, LPRECT lpRect)
{

	/*int nLeftRect = x - radius, nTopRect = x - radius,
	nRightRect = x + radius, nBottomRect = x + radius;
	if (nTopRect <= lpRect->top) return CHAM_DINH;
	if (nBottomRect >= lpRect->bottom) return CHAM_DAY;
	if (nLeftRect <= lpRect->left) return CHAM_TRAI;
	if (nRightRect >= lpRect->right) return CHAM_PHAI;*/

	if (y <= lpRect->top) return CHAM_DINH;
	if (y >= lpRect->bottom) return CHAM_DAY;
	if (x <= lpRect->left) return CHAM_TRAI;
	if (x >= lpRect->right) return CHAM_PHAI;

	return 0;
}

void NewRect(LPRECT lpRect)
{
	lpRect->bottom -= CIRCLE_RADIUS;
	lpRect->right -= CIRCLE_RADIUS;
	lpRect->top += CIRCLE_RADIUS;
	lpRect->left += CIRCLE_RADIUS;
}
