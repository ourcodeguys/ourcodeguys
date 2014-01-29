#include <windows.h>
#include <string.h>
#include "resource.h"


INT_PTR CALLBACK DlgProc(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam);
DWORD WINAPI vkListFile(PVOID pParam);

char szPath[MAX_PATH];
int iAttr = DDL_READWRITE;
/* Windows File Attributes:
* DDL_READWRITE	= 0x0000 -> Normal Files
* DLL_READONLY		= 0x0001 -> Read-only Files
* DLL_HIDDEN		= 0x0002 -> Hidden Files
* DLL_SYSTEM		= 0x0004 -> System Files
* DLL_DIRECTORY	= 0x0010 -> Directory
* DLL_ARCHIVE		= 0x0020 -> Archive Files
*/
HANDLE hThread;
int iThreadID;
HWND hWnd;
BOOL bFlag = FALSE;
HANDLE hFile = 0;
HWND hBtnScanStop, hListPath, hTextBox;

INT WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
	LPSTR lpCmdLine, int nCmdShow)
{
	DialogBoxParamA(hInstance, MAKEINTRESOURCEA(IDD_DLG_FL),
		hWnd, DlgProc, NULL);
	return FALSE;
}

INT_PTR CALLBACK DlgProc(HWND hWndDlg, UINT Msg, WPARAM wParam, LPARAM lParam)
{
	switch(Msg)
	{
	case WM_INITDIALOG:
		iAttr = DDL_READWRITE;
		hBtnScanStop = GetDlgItem(hWndDlg, IDC_BTN_SS);
		hListPath = GetDlgItem(hWndDlg, IDC_LIST_PATH);
		hTextBox = GetDlgItem(hWndDlg, IDC_EDIT_PATH);
		SetFocus(hTextBox);
		return TRUE;
	case WM_SETFOCUS:
		SetFocus(GetDlgItem(hWndDlg, IDC_EDIT_PATH));
		return TRUE;
	case WM_COMMAND:
		switch(wParam)
		{
		case IDC_BTN_CLOSE:
			EndDialog(hWndDlg, 0);
			return TRUE;
			break;

		case IDC_BTN_SS:
			if (!bFlag)
			{
				/*Scan-Hit*/
				SetWindowTextA(hBtnScanStop, "Stop");
				GetWindowTextA(hTextBox, szPath, MAX_PATH);
				strcat(szPath, "\\*.*");
				/*SendMessageA(hListPath, LB_DIR, iAttr, (LPARAM) szPath);
				SendMessageA(hListPath, LBS_SORT, 0, 0);
				MessageBoxA(NULL, "Done!", "Message", MB_ICONINFORMATION);*/
				hThread = CreateThread(NULL, 0, vkListFile, hWndDlg, 0, (LPDWORD) &iThreadID);
				EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_HIDDEN), FALSE);
				EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_SYSTEM), FALSE);
				EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_NORMAL), FALSE);
				EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_ALL), FALSE);
				EnableWindow(GetDlgItem(hWndDlg, IDC_EDIT_PATH), FALSE);
			}
			else
			{
				/*Stop-Hit*/
				SetWindowTextA(hBtnScanStop, "Scan");
				TerminateThread(hThread, 0);
				EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_HIDDEN), TRUE);
				EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_SYSTEM), TRUE);
				EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_NORMAL), TRUE);
				EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_ALL), TRUE);
				EnableWindow(GetDlgItem(hWndDlg, IDC_EDIT_PATH), TRUE);
			}
			bFlag = !bFlag;
			break;

		case IDC_CHCK_NORMAL:
			if ((int) SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_NORMAL),
				BM_GETCHECK, 0, 0))
			{
				iAttr = DDL_READWRITE;
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_HIDDEN),
					BM_SETCHECK, 0, 0);
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_SYSTEM),
					BM_SETCHECK, 0, 0);
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_ALL),
					BM_SETCHECK, 0, 0);
			}
			else
			{
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_HIDDEN),
					BM_SETCHECK, 1, 0);
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_SYSTEM),
					BM_SETCHECK, 1, 0);
			}
			break;

		case IDC_CHCK_HIDDEN:
			if (SendMessageA(
				GetDlgItem(hWndDlg, IDC_CHCK_HIDDEN),
				BM_GETCHECK, 0, 0))
			{
				iAttr = DDL_HIDDEN;
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_NORMAL),
					BM_SETCHECK, 0, 0);
			}
			else
			{
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_ALL),
					BM_SETCHECK, 0, 0);
			}
			break;

		case IDC_CHCK_SYSTEM:
			if (SendMessageA(
				GetDlgItem(hWndDlg, IDC_CHCK_SYSTEM),
				BM_GETCHECK, 0, 0))
			{
				iAttr = DDL_SYSTEM;
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_NORMAL),
					BM_SETCHECK, 0, 0);
			}
			else
			{
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_ALL),
					BM_SETCHECK, 0, 0);
			}
			break;

		case IDC_CHCK_ALL:
			if ((int) SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_ALL),
				BM_GETCHECK, 0, 0))
			{
				iAttr = DDL_READWRITE | DDL_DIRECTORY | DDL_READONLY | DDL_SYSTEM | DDL_HIDDEN;
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_HIDDEN),
					BM_SETCHECK, 1, 0);
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_SYSTEM),
					BM_SETCHECK, 1, 0);
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_NORMAL),
					BM_SETCHECK, 0, 0);
			}
			else
			{
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_HIDDEN),
					BM_SETCHECK, 0, 0);
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_SYSTEM),
					BM_SETCHECK, 0, 0);
				SendMessageA(GetDlgItem(hWndDlg, IDC_CHCK_NORMAL),
					BM_SETCHECK, 1, 0);
			}
			break;
		}
		break;
	case WM_DESTROY:
		EndDialog(hWndDlg, 0);
		PostQuitMessage(0);
		return TRUE;
	}

	return FALSE;
}
DWORD WINAPI vkListFile(PVOID pParam)
{
	HWND hWndDlg = (HWND) pParam;
	SendMessageA(hListPath, LB_RESETCONTENT, 0, 0);
	SendMessageA(hListPath, LB_DIR, iAttr, (LPARAM) szPath);
	SendMessageA(hListPath, LBS_SORT, 0, 0);
	MessageBoxA(NULL, "Done!", "Message", MB_ICONINFORMATION);
	SetWindowTextA(hBtnScanStop, "Scan");
	EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_HIDDEN), TRUE);
	EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_SYSTEM), TRUE);
	EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_NORMAL), TRUE);
	EnableWindow(GetDlgItem(hWndDlg, IDC_CHCK_ALL), TRUE);
	EnableWindow(GetDlgItem(hWndDlg, IDC_EDIT_PATH), TRUE);
	bFlag = !bFlag;
	ExitThread(0);
}